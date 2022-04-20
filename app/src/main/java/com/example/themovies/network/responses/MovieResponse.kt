package com.example.themovies.network.responses

import com.example.themovies.data.Movie
import com.google.gson.annotations.SerializedName

class MovieResponse {
    @SerializedName("results")
    lateinit var results: List<Movie>
}