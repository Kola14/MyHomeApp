package com.example.myhomeapp.remote

import com.example.myhomeapp.BuildConfig
import com.example.myhomeapp.remote.api.SignalsApi
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitApi @Inject constructor(
        private val gson: Gson
) {
    private val URL = "URL"
    private val signals by lazy { getRetrofit(URL).create(SignalsApi::class.java) }

    private fun getRetrofit(baseUrl: String): Retrofit {

        val client = OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .apply {
                    if (BuildConfig.DEBUG) {
                        val logger = HttpLoggingInterceptor()
                        logger.level = HttpLoggingInterceptor.Level.BODY
                        this.addNetworkInterceptor(logger)
                    }
                }
                .build()

        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
    }

    private suspend fun <T> responseWrapper(block: suspend () -> Response<T>): Resource<T> {
        return safeApiCall(Dispatchers.IO, block)
    }

    companion object {
        private const val TIMEOUT = 1800L
    }


    suspend fun getRecent(
            userId: Int?
    ): String? {
        var gson = Gson()

        return gson.toJson("\"rows\"[{\"id\":\"201\", \"date\":\"01.04.2021\", \"time\":\"11:15\", \"isConfirmed\": False}]")
    }

    suspend fun getEventHistory(
            userId: Int?
    ): String? {
        return gson.toJson("\"rows\"[{\"id\":\"201\", \"date\":\"01.04.2021\", \"time\":\"11:15\", \"isConfirmed\": False}]")
    }
}