package com.example.themovies.api

import com.example.themovies.network.responses.PeopleResponse
import retrofit2.http.GET

interface PeopleApi {

    @GET("person/popular")
    suspend fun getPopularPeople(): PeopleResponse?

}