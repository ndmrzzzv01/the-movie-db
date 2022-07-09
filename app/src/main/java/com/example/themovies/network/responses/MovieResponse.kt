package com.example.themovies.network.responses

import com.example.themovies.network.data.Movie
import com.google.gson.annotations.SerializedName

class MovieResponse {
    @SerializedName("results")
    var results: List<Movie>? = null
}