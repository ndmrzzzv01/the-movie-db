package com.example.themovies.network.data

import com.google.gson.annotations.SerializedName

data class PosterSize(
    @SerializedName("poster_sizes") var posterSizes: List<String>? = mutableListOf(),
)
