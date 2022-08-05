package com.example.themovies.api

import com.example.themovies.screens.registration.data.Session
import com.example.themovies.screens.registration.data.Token
import com.example.themovies.screens.registration.data.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthenticationApi {

    @GET("authentication/token/new")
    suspend fun getRequestToken(): Token

    @POST("authentication/session/new")
    suspend fun createSession(
        @Body token: String?
    ): Session

    @GET("account?session_id={session_id}")
    suspend fun getDetailsAboutAccount(
        @Path("session_id", encoded = true) sessionId: String?
    ): User
}