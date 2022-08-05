package com.example.themovies.screens.registration.data

import com.google.gson.annotations.SerializedName

data class Token(
    val success: Boolean? = null,
    @SerializedName("request_token") val token: String? = null
)