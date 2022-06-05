package com.example.themovies.network.responses

import com.example.themovies.data.Season
import com.google.gson.annotations.SerializedName

class SeasonResponse {
    @SerializedName("seasons")
    val seasons: List<Season>? = null
}