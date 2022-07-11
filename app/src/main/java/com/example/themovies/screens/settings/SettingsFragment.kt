package com.example.themovies.screens.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.themovies.databinding.FragmentSettingsBinding
import com.example.themovies.notifications.ScheduledNotificationWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    companion object {
        const val NOTIFICATION_LIKE = "notification_like"
        const val NOTIFICATION_UPDATE = "notification_update"
        const val NOTIFICATION_CHANNEL_ID = "movie_id"
        const val NOTIFICATION_WORK = "notification_work"
        const val NAME = "name"
        const val NAME_CHANNEL = "channel"
    }

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        notificationSettingsWithLikes()
        notificationSettingsWithUpdateList()

        return binding.root
    }

    private fun notificationSettingsWithLikes() {
        binding.apply {
            val editor = sharedPreferences.edit()
            swLiked.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    editor?.putBoolean(NOTIFICATION_LIKE, true)
                    editor?.commit()
                } else {
                    editor?.putBoolean(NOTIFICATION_LIKE, false)
                    editor?.commit()
                }
            }
            swLiked.isChecked = sharedPreferences.getBoolean(NOTIFICATION_LIKE, false)
        }
    }

    private fun notificationSettingsWithUpdateList() {
        binding.apply {
            val editor = sharedPreferences.edit()
            swUpdateList.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    editor?.putBoolean(NOTIFICATION_UPDATE, true)
                    editor?.commit()
                    scheduleNotification()
                } else {
                    editor?.putBoolean(NOTIFICATION_UPDATE, false)
                    editor?.commit()
                }
            }
            swUpdateList.isChecked = sharedPreferences.getBoolean(NOTIFICATION_UPDATE, false)
        }

    }

    private fun scheduleNotification() {
        val worker = OneTimeWorkRequest.Builder(ScheduledNotificationWorker::class.java)
            .setInitialDelay(180, TimeUnit.MINUTES)
            .build()
        val instanceWorkManager = WorkManager.getInstance(requireContext())
        instanceWorkManager.beginUniqueWork(NOTIFICATION_WORK, ExistingWorkPolicy.REPLACE, worker)
            .enqueue()
    }

}