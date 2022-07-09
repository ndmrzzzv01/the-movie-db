package com.example.themovies.network.responses

import com.example.themovies.network.data.People
import com.google.gson.annotations.SerializedName

class PeopleResponse {
    @SerializedName("results")
    var results: List<People>? = null
}