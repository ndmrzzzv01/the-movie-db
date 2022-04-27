package com.example.themovies.utils

import android.content.Context
import android.net.ConnectivityManager
import com.example.themovies.api.ConfigurationApi
import com.example.themovies.api.MovieApi
import com.example.themovies.api.TvApi
import com.example.themovies.network.TheMovieDBInterceptor
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkUtils {

    const val API_KEY = "d906252ac4f180bbf851fd1bf9e97f9b"

    fun isNetworkConnected(context: Context): Boolean {
        val connect: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager
        return connect.activeNetworkInfo != null && connect.activeNetworkInfo!!.isConnected
    }

}