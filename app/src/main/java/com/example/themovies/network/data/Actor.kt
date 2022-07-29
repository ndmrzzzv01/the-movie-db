package com.example.themovies.network.data

import com.google.gson.annotations.SerializedName

data class Actor(
    val name: String? = null,
    @SerializedName("profile_path") val profilePath: String? = null,
)