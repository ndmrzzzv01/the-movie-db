package com.example.themovies.utils

import android.content.Context
import android.net.ConnectivityManager
import com.example.themovies.network.interceptor.ApiKeyInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkUtils {

    fun isNetworkConnected(context: Context): Boolean {
        val connect: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager
        return connect.activeNetworkInfo != null && connect.activeNetworkInfo!!.isConnected
    }

    fun createRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

}