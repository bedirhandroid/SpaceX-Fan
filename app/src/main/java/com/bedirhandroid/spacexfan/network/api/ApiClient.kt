package com.bedirhandroid.spacexfan.network.api

import com.bedirhandroid.spacexfan.util.Constant.API_TIMEOUT
import com.bedirhandroid.spacexfan.util.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiClient {

    @Singleton
    @Provides
    //init client with base url
    fun getClient(): ApiService {
        val retrofit: ApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client())
                .build()
                .create(ApiService::class.java)
        }
        return retrofit
    }

    private fun client(): OkHttpClient {
        return with(OkHttpClient.Builder()) {
            callTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            this.build()
        }
    }
}