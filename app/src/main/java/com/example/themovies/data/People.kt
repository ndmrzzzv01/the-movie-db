package com.example.themovies.data

import com.google.gson.annotations.SerializedName

data class People(
    val id: Int? = null,
    val name: String? = null,
    @SerializedName("profile_path") val profilePath: String? = null
)
