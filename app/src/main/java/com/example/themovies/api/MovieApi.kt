package com.example.themovies.api

import com.example.themovies.network.data.Movie
import com.example.themovies.network.responses.CreditResponse
import com.example.themovies.network.responses.MovieResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
    ): MovieResponse?

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id", encoded = true) movieId: Int?
    ): Movie?

    @GET("movie/{movie_id}/credits")
    suspend fun getCredits(
        @Path("movie_id", encoded = true) movieId: Int?
    ): CreditResponse
}
