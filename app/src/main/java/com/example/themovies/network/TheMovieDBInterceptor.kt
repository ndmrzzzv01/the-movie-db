package com.example.themovies.network

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class TheMovieDBInterceptor : Interceptor {

    companion object {
        const val API_KEY = "d906252ac4f180bbf851fd1bf9e97f9b"
    }

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