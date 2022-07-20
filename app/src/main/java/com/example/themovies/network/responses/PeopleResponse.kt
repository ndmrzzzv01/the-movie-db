package com.example.themovies.network.responses

import com.example.themovies.network.data.Person
import com.google.gson.annotations.SerializedName

class PeopleResponse {
    @SerializedName("results")
    var results: List<Person>? = null
}
