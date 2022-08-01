package com.example.themovies.network.data

data class MediaItemType(
    val id: Int? = 0,
    val url: String? = null,
    val name: String,
    val customParameter: Any? = null
)
