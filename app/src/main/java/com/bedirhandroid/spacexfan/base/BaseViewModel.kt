package com.bedirhandroid.spacexfan.base

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bedirhandroid.spacexfan.network.response.rockets.SpaceXRocketsResponseItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import java.io.EOFException
import java.net.ProtocolException
import java.util.concurrent.TimeoutException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel : ViewModel() {
    val errorLiveData: MutableLiveData<String> = MutableLiveData()
    val showProgress: MutableLiveData<Boolean> = MutableLiveData()
    val mutableFavList = MutableLiveData<ArrayList<SpaceXRocketsResponseItem>>()
    val auth = FirebaseAuth.getInstance()
    val dataBase = FirebaseDatabase.getInstance()


    //inline coroutines scope
    protected inline fun sendRequest(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        crossinline block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            showProgress.postValue(true)
            try {
                block()
            } catch (exception: Exception) {
                when (exception) {
                    is TimeoutException -> errorLiveData.postValue(ErrorMessages.TIME_OUT.toString())
                    is ProtocolException -> errorLiveData.postValue(ErrorMessages.TRY_AGAIN.toString())
                    is EOFException -> errorLiveData.postValue(ErrorMessages.ERROR_EOFE.toString())
                    else -> {
                        errorLiveData.postValue(ErrorMessages.UNKNOWN_ERROR.toString())
                    }
                }
            } finally {
                showProgress.postValue(false)
            }
        }
    }

    fun getDataBaseList() {
        dataBase.getReference("users").child(auth.uid!!)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val favList = arrayListOf<SpaceXRocketsResponseItem>()
                    for (itemSnapshot in snapshot.children) {
                        val item = itemSnapshot.getValue(SpaceXRocketsResponseItem::class.java)
                        item?.let(favList::add)
                    }
                    mutableFavList.postValue(favList)
                }

                override fun onCancelled(error: DatabaseError) {
                    errorLiveData.postValue(error.message)
                }

            })
    }

    fun addDataBaseOperation(data: SpaceXRocketsResponseItem) {
        dataBase.getReference("users").child(auth.uid!!)
            .child(data.id.toString())
            .setValue(data)
    }


    fun removeDataBaseOperation(data: SpaceXRocketsResponseItem) {
        dataBase.getReference("users").child(auth.uid!!)
            .child(data.id.toString()).removeValue()
    }
}