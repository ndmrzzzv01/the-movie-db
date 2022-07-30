package com.example.themovies.network.data

import com.google.gson.annotations.SerializedName

data class Collection(
    @SerializedName("backdrop_path") var backdropPath: String = "",
): RecordType()
