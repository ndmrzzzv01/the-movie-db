package com.example.themovies.network

import com.example.themovies.network.data.MediaItemType

fun interface OnMediaTypeClick {
    fun invoke(type: MediaItemType)
}