package com.example.themovies.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.themovies.R
import com.example.themovies.screens.activities.NavigationActivity
import com.example.themovies.screens.settings.SettingsFragment

class NotificationService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
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
            Intent(this.applicationContext, NavigationActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
            }

        val notification =
            NotificationCompat.Builder(this, SettingsFragment.NOTIFICATION_CHANNEL_ID)
                .setContentTitle("You like something!")
                .setContentText("You like '${intent?.getStringExtra("name")}' !")
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true)
                .build()

        notificationManager?.notify(0, notification)

        return START_NOT_STICKY
    }

}