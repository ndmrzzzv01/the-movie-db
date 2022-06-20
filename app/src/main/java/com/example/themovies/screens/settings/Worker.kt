package com.example.themovies.screens.settings

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.themovies.R
import com.example.themovies.activities.MainActivity

class Worker(var context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        try {
            var notificationManager: NotificationManager? = null

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    SettingsFragment.NOTIFICATION_CHANNEL_ID,
                    "Movie",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager = context.getSystemService(NotificationManager::class.java)
                notificationManager?.createNotificationChannel(channel)
            }

            val contentIntent =
                Intent(this.applicationContext, MainActivity::class.java).let { notificationIntent ->
                    PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
                }

            val notification =
                NotificationCompat.Builder(context, SettingsFragment.NOTIFICATION_CHANNEL_ID)
                    .setContentTitle("A little notification!")
                    .setContentText("List of movie, tv or people update right now!")
                    .setContentIntent(contentIntent)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setAutoCancel(true)
                    .build()

            notificationManager?.notify(0, notification)

        } catch (ex: Exception) {
            return Result.failure()
        }
        return Result.retry()
    }
}