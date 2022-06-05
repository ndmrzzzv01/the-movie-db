package com.example.themovies.api

import com.example.themovies.data.Season
import com.example.themovies.data.TV
import com.example.themovies.network.responses.SeasonResponse
import com.example.themovies.network.responses.TVResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvApi {

    @GET("tv/popular")
    suspend fun getPopularTV(
        @Query("page") page: Int,
    ): TVResponse?

    @GET("tv/{tv_id}")
    suspend fun getTV(
        @Path("tv_id", encoded = true) tvId: Int?
    ): TV?

    @GET("tv/{tv_id}")
    suspend fun getTVSeason(
        @Path("tv_id", encoded = true) tvId: Int?
    ): SeasonResponse?

}