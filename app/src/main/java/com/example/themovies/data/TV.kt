package com.example.themovies.data

import com.google.gson.annotations.SerializedName

data class TV(
    val id: Int? = null,
    @SerializedName("poster_path") val posterPath: String? = "",
    val name: String? = ""
)