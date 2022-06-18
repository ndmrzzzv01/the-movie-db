package com.example.themovies.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.themovies.databinding.FragmentSettingsBinding
import com.example.themovies.utils.SettingsUtils

class SettingsFragment : Fragment() {

    companion object {
        const val NOTIFICATION_LIKE = "notification_like"
        const val NOTIFICATION_CHANNEL_ID = "movie_id"
    }

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        notificationSettings()

        return binding.root
    }

    private fun notificationSettings() {
        binding.apply {
            val editor = SettingsUtils.provideSharedPreferences(requireContext())?.edit()
            swLiked.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    editor?.putBoolean(NOTIFICATION_LIKE, true)
                    editor?.commit()
                } else {
                    editor?.putBoolean(NOTIFICATION_LIKE, false)
                    editor?.commit()
                }
            }
            swLiked.isChecked = SettingsUtils.provideSharedPreferences(requireContext())
                ?.getBoolean(NOTIFICATION_LIKE, false)!!
        }
    }

}