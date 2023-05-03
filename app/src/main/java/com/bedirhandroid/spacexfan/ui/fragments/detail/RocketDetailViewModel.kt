package com.bedirhandroid.spacexfan.ui.fragments.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bedirhandroid.spacexfan.base.BaseViewModel
import com.bedirhandroid.spacexfan.base.ErrorMessages
import com.bedirhandroid.spacexfan.base.Repo
import com.bedirhandroid.spacexfan.network.response.rockets.SpaceXRocketsResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class RocketDetailViewModel @Inject constructor(private val repo: Repo) : BaseViewModel() {

    private val mutableRocketDetailLiveData = MutableLiveData<SpaceXRocketsResponseItem>()
    val rocketDetailLiveData : LiveData<SpaceXRocketsResponseItem> get() = mutableRocketDetailLiveData

    fun getRocketDetails(id: String) {
        sendRequest {
            repo.getRocketDetail(id).collectLatest {
                it?.let(mutableRocketDetailLiveData::postValue)?: kotlin.run {
                    errorLiveData.postValue(ErrorMessages.ERROR)
                }
            }
        }
    }
}