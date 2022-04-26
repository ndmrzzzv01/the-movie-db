package com.example.themovies.network.responses

import com.example.themovies.data.TV
import com.google.gson.annotations.SerializedName

class TVResponse {
    @SerializedName("results")
    var results: List<TV>? = null
}