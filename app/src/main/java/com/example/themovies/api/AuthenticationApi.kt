package com.example.themovies.api

import com.example.themovies.screens.registration.data.Session
import com.example.themovies.screens.registration.data.Token
import com.example.themovies.screens.registration.data.User
import retrofit2.http.*

interface AuthenticationApi {

    @GET("authentication/token/new")
    suspend fun getRequestToken(): Token?

    @FormUrlEncoded
    @POST("authentication/session/new")
    suspend fun createSession(
        @Field("request_token") token: String
    ): Session?

    @GET("account")
    suspend fun getDetailsAboutAccount(
        @Query("session_id", encoded = true) sessionId: String?
    ): User
}