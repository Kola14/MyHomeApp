package com.example.myhomeapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.os.Build
import android.os.Handler
import com.example.myhomeapp.di.DaggerAppComponent
import com.example.myhomeapp.utils.NotificationsReceiver
import java.util.*

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

    val receiver: BroadcastReceiver = NotificationsReceiver()

    fun getNotificationsReceiver(): BroadcastReceiver{
        return receiver
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

    val handler = Handler()
    val mytimer = Timer()
    var flag = false
    var globalNotificationsId = 0

    fun getNotificationId(): Int{
        globalNotificationsId++
        return globalNotificationsId
    }

    var timerCreated = false

    fun changeTimerStatus(){
        timerCreated = true
    }
    fun getTimerStatus(): Boolean{
        return timerCreated
    }

    fun getTimer(): Timer{
        return mytimer
    }

    fun killTimer(){
        mytimer.cancel()
    }

    fun runTimer(job: Unit){
        val handler = Handler()
        val doAsynchronousTask: TimerTask = object : TimerTask() {
            override fun run() {
                handler.post {
                    try {
                        job.run {  }

                        if (flag){
                            flag = false
                            mytimer.cancel()
                        }
                    } catch (e: Exception) {

                    }
                }
            }
        }
        mytimer.schedule(doAsynchronousTask, 0, 5000)
    }

}