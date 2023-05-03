package com.bedirhandroid.spacexfan.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bedirhandroid.spacexfan.network.api.ApiService
import com.bedirhandroid.spacexfan.network.response.rockets.SpaceXRocketsResponseItem
import java.io.IOException
import javax.inject.Inject

class PagingListAdapter @Inject constructor(private val apiService: ApiService) :
    PagingSource<Int, SpaceXRocketsResponseItem>() {
    override fun getRefreshKey(state: PagingState<Int, SpaceXRocketsResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SpaceXRocketsResponseItem> {
        val position = params.key ?: 0
        return try {
            val response = apiService.getRocketsList(position)
            LoadResult.Page(
                data = response,
                prevKey = if (position == 0) null else position,
                nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (exc: IOException) {
            return LoadResult.Error(exc)
        } catch (exc: IOException) {
            return LoadResult.Error(exc)
        }
    }

}