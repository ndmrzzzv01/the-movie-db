package com.example.themovies.api

import com.example.themovies.network.data.Person
import com.example.themovies.network.responses.CastOfMovie
import com.example.themovies.network.responses.CastOfTv
import com.example.themovies.network.responses.PeopleResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleApi {

    @GET("person/popular")
    suspend fun getPopularPeople(
        @Query("page") page: Int,
    ): PeopleResponse?

    @GET("person/{person_id}")
    suspend fun getPeople(
        @Path("person_id", encoded = true) personId: Int?
    ): Person?

    @GET("person/{movie_id}/movie_credits")
    suspend fun getCastForMovie(
        @Path("movie_id", encoded = true) movieId: Int?
    ): CastOfMovie

    @GET("person/{tv_id}/tv_credits")
    suspend fun getCastForTv(
        @Path("tv_id", encoded = true) tvId: Int?
    ): CastOfTv

}