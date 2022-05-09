package com.example.themovies.data

import com.google.gson.annotations.SerializedName

data class TV(
    val id: Int? = null,
    @SerializedName("poster_path") val posterPath: String? = "",
    @SerializedName("backdrop_path") val backdropPath: String? = "",
    val name: String? = "",
    @SerializedName("original_name") val originalName: String = "",
    val homepage: String? = "",
    @SerializedName("first_air_date") val releaseDate: String? = "",
    val status: String? = "",
    @SerializedName("vote_average") val voteAverage: Double? = 0.0,
    val overview: String? = ""
) : RecordType()