package com.example.themovies.views.adapters.binding

import androidx.databinding.BindingAdapter
import com.like.LikeButton

@BindingAdapter("isLikedButton")
fun isLikedButton(view: LikeButton, value: Boolean?) {
    view.isLiked = value != false
}