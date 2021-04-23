package com.example.myhomeapp.remote

import com.example.myhomeapp.BuildConfig
import com.example.myhomeapp.models.DevicesWrapper
import com.example.myhomeapp.models.SignalsWrapper
import com.example.myhomeapp.remote.api.SignalsApi
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitApi @Inject constructor(
        private val gson: Gson
) {
    private val URL = "http://185.197.74.219:8000/"
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



    /*
    suspend fun getRecent(
            userId: Int?
    ): Resource<SignalsWrapper> {

        //return signals.getRecent(userId)
        val list = mutableListOf<Signals>()
        list.add(Signals(1,"01.04.2021","11:15", false))
        list.add(Signals(2,"01.04.2021","11:25", false))
        list.add(Signals(3,"01.04.2021","11:35", false))
        list.add(Signals(4,"01.04.2021","11:45", true))
        list.add(Signals(5,"01.04.2021","11:55", true))
        list.add(Signals(6,"01.04.2021","12:45", false))
        list.add(Signals(7,"01.04.2021","12:55", false))

        return Resource.success(SignalsWrapper(list))
    }


     */

    suspend fun getRecent(
            userId: Int?
    ): Resource<SignalsWrapper> {
        return responseWrapper {
             //signals.getRecent(userId)
            signals.getRecent()
        }
    }

    suspend fun getEventHistory(
            userId: Int?
    ): Resource<SignalsWrapper> {
        return responseWrapper {
            //signals.getEventHistory(userId)
            signals.getEventHistory()
        }
    }

    fun confirmSignal(
            signalId: Int?
    ) {
       signals.confirmSignal(signalId).enqueue(object : Callback<Void> {
           override fun onFailure(call: Call<Void>?, t: Throwable?) {
               // failure
           }

           override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
               // success
           }
       })
    }

    suspend fun getDevices(
            userId: Int?
    ): Resource<DevicesWrapper> {
        return responseWrapper {
            //signals.getEventHistory(userId)
            signals.getDevices()
        }
    }

    suspend fun clearHistory() {
        signals.clearHistory().enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                // failure
            }

            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                // success
            }
        })
    }


/*
    suspend fun getEventHistory(
            userId: Int?
    ): Resource<SignalsWrapper> {
        //return signals.getEventHistory(userId)
        val list = mutableListOf<Signals>()
        list.add(Signals(1,"01.04.2021","11:15", false))
        list.add(Signals(2,"01.04.2021","11:25", false))
        list.add(Signals(3,"01.04.2021","11:35", false))
        list.add(Signals(4,"01.04.2021","11:45", true))
        list.add(Signals(5,"01.04.2021","11:55", true))
        list.add(Signals(6,"01.04.2021","12:45", false))
        list.add(Signals(7,"01.04.2021","12:55", false))

        return Resource.success(SignalsWrapper(list))
    }

 */
}