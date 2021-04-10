package com.example.myhomeapp.remote.api

import com.example.myhomeapp.models.SignalsWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface SignalsApi {
    @GET("api/getrecent")
    suspend fun getRecent(
            @Query("userId") query: Int?
    ): Response<SignalsWrapper>

    @GET("api/geteventhistory")
    suspend fun getEventHistory(
            @Query("userId") query: Int?
    ): Response<SignalsWrapper>

    @GET("api/confirm")
    suspend fun confirmSignal(
            @Query("singalId") query: Int?
    )

    @GET("api/clearHistory")
    suspend fun clearHistory(
            @Query("userId") query: Int?
    )
}