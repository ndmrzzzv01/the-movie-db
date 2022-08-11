package com.example.themovies.screens.registration.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Session(
    val success: Boolean? = null,
    @SerializedName("session_id") val id: String? = null
): Serializable
