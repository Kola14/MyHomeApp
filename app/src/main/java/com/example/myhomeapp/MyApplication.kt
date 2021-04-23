package com.example.myhomeapp

import android.app.Application
import com.example.myhomeapp.di.DaggerAppComponent

class MyApplication: Application() {

    val appComponent = DaggerAppComponent.create()

    var isUpdatable = true
    var pushNotificationsEnabled = true

    fun getUpdatableStatus(): Boolean{
        return isUpdatable
    }

    fun setUpdatableStatus(newStatus: Boolean){
        isUpdatable = newStatus
    }

    fun getPushNotificationsStatus(): Boolean{
        return pushNotificationsEnabled
    }

    fun setPushNotificationsStatus(newStatus: Boolean){
        pushNotificationsEnabled = newStatus
    }
}