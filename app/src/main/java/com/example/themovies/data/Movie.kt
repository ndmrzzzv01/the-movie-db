package com.example.themovies.data

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int? = null,
    val title: String = "",
    @SerializedName("poster_path") var posterPath: String? = "",
)
