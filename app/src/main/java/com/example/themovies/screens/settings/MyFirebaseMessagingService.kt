package com.example.themovies.screens.settings

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.themovies.activities.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        sendNotification(message)
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        var notificationManager: NotificationManager? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                SettingsFragment.NOTIFICATION_CHANNEL_ID,
                "Movie",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager = this.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }

        val contentIntent =
            Intent(this.applicationContext, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
            }

        val notification =
            NotificationCompat.Builder(this, SettingsFragment.NOTIFICATION_CHANNEL_ID)
                .setContentTitle(remoteMessage.notification?.title)
                .setContentText(remoteMessage.notification?.body)
                .setContentIntent(contentIntent)
                .setSmallIcon(com.example.themovies.R.drawable.ic_launcher_foreground)
                .setAutoCancel(true)
                .build()

        notificationManager?.notify(0, notification)
    }
}