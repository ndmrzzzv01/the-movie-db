package com.example.themovies.notifications

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
import com.example.themovies.screens.activities.NavigationActivity
import com.example.themovies.screens.settings.SettingsFragment

class ScheduledNotificationWorker(var context: Context, params: WorkerParameters) :
    Worker(context, params) {
    override fun doWork(): Result {
        try {
            var notificationManager: NotificationManager? = null

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    SettingsFragment.NOTIFICATION_CHANNEL_ID,
                    SettingsFragment.NAME_CHANNEL,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager = context.getSystemService(NotificationManager::class.java)
                notificationManager?.createNotificationChannel(channel)
            }

            val contentIntent =
                Intent(
                    this.applicationContext,
                    NavigationActivity::class.java
                ).let { notificationIntent ->
                    PendingIntent.getActivity(
                        context,
                        0,
                        notificationIntent,
                        PendingIntent.FLAG_IMMUTABLE
                    )
                }

            val notification =
                NotificationCompat.Builder(context, SettingsFragment.NOTIFICATION_CHANNEL_ID)
                    .setContentTitle(context.getString(R.string.notification_about_updates_list))
                    .setContentText(context.getString(R.string.message_notification_about_update))
                    .setContentIntent(contentIntent)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setAutoCancel(true)
                    .build()

            notificationManager?.notify(0, notification)

        } catch (ex: Exception) {
            return Result.failure()
        }
        return Result.success()
    }
}