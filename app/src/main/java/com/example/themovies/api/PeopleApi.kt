package com.example.themovies.api

import com.example.themovies.network.data.Person
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

//    @GET("movie/{movie_id}")
//    suspend fun getMovieForKnownPerson(
//        @Path("movie_id", encoded = true) movieId: Int?
//    ): Movie?
}