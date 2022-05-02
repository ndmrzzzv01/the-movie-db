package com.example.themovies.api

import com.example.themovies.network.responses.PeopleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PeopleApi {

    @GET("person/popular")
    suspend fun getPopularPeople(
        @Query("page") page: Int,
    ): PeopleResponse?

}