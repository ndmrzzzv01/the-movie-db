package com.example.themovies.network

import com.example.themovies.utils.NetworkUtils.API_KEY
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class TheMovieDBInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newUrl: HttpUrl = request.url().newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }

}