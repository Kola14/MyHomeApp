package com.example.myhomeapp.remote.api

import com.example.myhomeapp.models.DevicesWrapper
import com.example.myhomeapp.models.SignalsWrapper
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface SignalsApi {
    @GET("api/getrecent/1001")
    suspend fun getRecent(
            //@Query("userId") query: Int?
    ): Response<SignalsWrapper>

    @GET("api/geteventhistory/1001")
    suspend fun getEventHistory(
            //@Query("userId") query: Int?
    ): Response<SignalsWrapper>

    @GET("api/confirm/")
    fun confirmSignal(
            @Query("signalId") query: Int?
    ): Call<Void>

    @GET("api/clearHistory/1001")
    suspend fun clearHistory(
           //@Query("userId") query: Int?
    ): Call<Void>

    @GET("api/getdevices/1001")
    suspend fun getDevices(
            //@Query("userId") query: Int?
    ): Response<DevicesWrapper>
}