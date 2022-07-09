package com.example.themovies.network.data

import com.google.gson.annotations.SerializedName

data class Season(
    @SerializedName("air_date") val airDate: String? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    val name: String? = null
)
