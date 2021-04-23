package com.example.myhomeapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.myhomeapp.di.DaggerAppComponent

class MyApplication: Application() {

    @Override
    override fun onCreate(){
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = getString(R.string.app_name)
            val descriptionText = "this app's channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val MyHomeAppChannel = NotificationChannel(CHANNEL_ID, "MyHomeApp channel", importance)
            MyHomeAppChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(MyHomeAppChannel)
        }
    }

    val appComponent = DaggerAppComponent.create()

    val CHANNEL_ID = "MyHomeAppChannel"

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