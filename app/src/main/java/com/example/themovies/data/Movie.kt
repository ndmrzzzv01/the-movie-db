package com.example.themovies.data

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int? = null,
    val budget: Long? = null,
    val revenue: Long? = null,
    val title: String = "",
    @SerializedName("original_title") val originalTitle: String? = "",
    @SerializedName("overview") val description: String = "",
    @SerializedName("poster_path") var posterPath: String? = "",
    @SerializedName("backdrop_path") var backdropPath: String = "",
): RecordType()
