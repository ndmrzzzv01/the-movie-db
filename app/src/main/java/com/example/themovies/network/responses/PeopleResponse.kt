package com.example.themovies.network.responses

import com.example.themovies.network.data.Gallery
import com.example.themovies.network.data.Movie
import com.example.themovies.network.data.Person
import com.example.themovies.network.data.TV
import com.google.gson.annotations.SerializedName

class PeopleResponse {
    @SerializedName("results")
    var results: List<Person>? = null
}

class CastOfMovie {
    @SerializedName("cast")
    var castOfMovie: List<Movie>? = null
}

class CastOfTv {
    @SerializedName("cast")
    var castOfTv: List<TV>? = null
}

class GalleryResponse {
    @SerializedName("profiles")
    var gallery: List<Gallery>? = null
}