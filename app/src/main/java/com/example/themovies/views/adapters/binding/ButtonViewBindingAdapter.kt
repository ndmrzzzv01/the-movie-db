package com.example.themovies.views.adapters.binding

import androidx.databinding.BindingAdapter
import com.like.LikeButton

@BindingAdapter("isLikedButton")
fun LikeButton.isLikedButton(value: Boolean?) {
    this.isLiked = value != false
}