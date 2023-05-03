package com.bedirhandroid.spacexfan.base

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bedirhandroid.spacexfan.network.api.ApiService
import com.bedirhandroid.spacexfan.network.response.rockets.SpaceXRocketsResponseItem
import com.bedirhandroid.spacexfan.paging.PagingListAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repo @Inject constructor(private val apiService : ApiService) {

    fun getRocketList() : Flow<PagingData<SpaceXRocketsResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {PagingListAdapter(apiService)}
        ).flow
    }

    suspend fun getRocketDetail(id: String) : Flow<SpaceXRocketsResponseItem?>  {
        return flow {
            emit(apiService.getRocketDetail(id))
        }
    }
}