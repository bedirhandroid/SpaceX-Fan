package com.bedirhandroid.spacexfan.network.api

import com.bedirhandroid.spacexfan.network.response.rockets.SpaceXRocketsResponse
import com.bedirhandroid.spacexfan.network.response.rockets.SpaceXRocketsResponseItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("rockets")
    suspend fun getRocketsList(
        @Query("offset") page: Int,
        @Query("limit") limit: Int = 1,
    ) : SpaceXRocketsResponse

    @GET("rockets/{rocketId}")
    suspend fun getRocketDetail(
        @Path (value = "rocketId") subUrl: String,
    ) : SpaceXRocketsResponseItem?
}