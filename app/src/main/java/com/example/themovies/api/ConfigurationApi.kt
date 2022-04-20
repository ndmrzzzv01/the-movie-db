package com.example.themovies.api

import com.example.themovies.network.responses.ConfigurationResponse
import retrofit2.http.GET

interface ConfigurationApi {
    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationResponse?
}