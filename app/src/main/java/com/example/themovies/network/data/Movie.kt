package com.example.themovies.network.data

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int? = null,
    val budget: Long? = null,
    val revenue: Long? = null,
    @SerializedName("title") val name: String = "",
    @SerializedName("release_date") val releaseDate: String? = "",
    @SerializedName("original_title") val originalName: String? = "",
    @SerializedName("overview") val description: String = "",
    @SerializedName("poster_path") var posterPath: String? = "",
    @SerializedName("backdrop_path") var backdropPath: String = "",
    @SerializedName("vote_average") var vote: String? = "",
    var runtime: String? = "",
    @SerializedName("belongs_to_collection") val collection: Collection? = null
) : RecordType()
