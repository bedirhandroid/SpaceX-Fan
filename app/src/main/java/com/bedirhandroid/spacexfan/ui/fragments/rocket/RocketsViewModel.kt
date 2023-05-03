package com.bedirhandroid.spacexfan.ui.fragments.rocket

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bedirhandroid.spacexfan.base.BaseViewModel
import com.bedirhandroid.spacexfan.base.Repo
import com.bedirhandroid.spacexfan.network.response.rockets.SpaceXRocketsResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RocketsViewModel @Inject constructor(private val repo : Repo) : BaseViewModel() {
    val getRocketsList : Flow<PagingData<SpaceXRocketsResponseItem>> =
        repo.getLauncherList().cachedIn(viewModelScope)
}