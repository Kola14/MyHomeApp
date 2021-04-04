package com.example.myhomeapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppProviderModule {

    /*@Provides
    @Singleton
    fun provideRetrofitApi(
        gson: Gson
    ):RetrofitApi {
        return RetrofitApi(gson)
    }*/

    @Provides
    @Singleton
    internal fun getGson(gsonBuilder: GsonBuilder): Gson {
        return gsonBuilder.setLenient().create()
    }

    @Provides
    @Singleton
    internal fun getGsonBuilder(): GsonBuilder {
        return GsonBuilder()
    }
}