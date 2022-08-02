package com.example.themovies.network.data

import com.google.gson.annotations.SerializedName

data class Gallery(
    @SerializedName("file_path") val path: String? = null
) : RecordType()
