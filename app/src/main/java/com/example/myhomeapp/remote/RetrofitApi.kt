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
    private val URL = ""
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



}
