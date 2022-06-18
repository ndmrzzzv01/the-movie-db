package com.example.themovies.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

object SettingsUtils {

    fun provideSharedPreferences(context: Context): SharedPreferences? {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

}