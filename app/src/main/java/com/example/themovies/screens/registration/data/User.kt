package com.example.themovies.screens.registration.data

import com.google.gson.annotations.SerializedName

data class User(
    val name: String? = null,
    @SerializedName("username") val username: String? = null
)
