package com.example.themovies.utils

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class ConnectivityTracker @Inject constructor() {

    fun isNetworkConnected(context: Context): Boolean {
        val connect: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager
        return connect.activeNetworkInfo != null && connect.activeNetworkInfo!!.isConnected
    }
}