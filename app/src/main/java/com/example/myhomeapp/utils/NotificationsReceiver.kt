package com.example.myhomeapp.utils

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.myhomeapp.MyApplication


class NotificationsReceiver : BroadcastReceiver() {
    @Override
    override fun onReceive(context: Context?, intent: Intent?) {

        Toast.makeText(context, "MyHomeApp: Получен сигнал", Toast.LENGTH_LONG).show();
        Log.d("OK", "AlarmReceiver.onReceive");
        if (context != null) {
            //showNotification(context)
        }
    }

    private fun showNotification(context: Context) {
        val contentIntent = PendingIntent.getActivity(context, 0,
                Intent(context, MyApplication::class.java), 0)
        val mBuilder = NotificationCompat.Builder(context)
                .setContentTitle("My notification")
                .setContentText("Test")
        mBuilder.setContentIntent(contentIntent)
        mBuilder.setDefaults(Notification.DEFAULT_SOUND)
        mBuilder.setAutoCancel(true)
        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(1, mBuilder.build())
    }
}