package com.example.themovies.api

import com.example.themovies.network.responses.TVResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TvApi {

    @GET("tv/popular")
    suspend fun getPopularTV(
        @Query("page") page: Int,
    ): TVResponse?

}