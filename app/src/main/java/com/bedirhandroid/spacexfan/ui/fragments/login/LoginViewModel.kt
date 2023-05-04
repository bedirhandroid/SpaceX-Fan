package com.bedirhandroid.spacexfan.ui.fragments.login

import androidx.lifecycle.MutableLiveData
import com.bedirhandroid.spacexfan.base.BaseViewModel
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : BaseViewModel() {
    val registerUserMutableLiveData = MutableLiveData<FirebaseUser>()
    val loginUserMutableLiveData = MutableLiveData<FirebaseUser>()
    fun registerUser(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener {
            registerUserMutableLiveData.postValue(it.user)
        }.addOnFailureListener {
            errorLiveData.postValue(it.message)
        }
    }

    fun loginUser(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener {
            loginUserMutableLiveData.postValue(it.user)
        }.addOnFailureListener {
            errorLiveData.postValue(it.message)
        }
    }

    fun signOut() = auth.signOut()
}