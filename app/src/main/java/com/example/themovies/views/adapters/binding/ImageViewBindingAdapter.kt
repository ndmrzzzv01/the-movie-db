package com.example.themovies.views.adapters.binding

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.themovies.network.repositories.ConfigurationRepository

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, liveUrl: String?) {
    if (liveUrl?.isNotEmpty() == true) {
        Glide
            .with(view)
            .load("${ConfigurationRepository.URL}${ConfigurationRepository.sizeOfPoster}${liveUrl}")
            .into(view)
    } else {
        view.background = ColorDrawable(Color.BLACK)
    }
}

@BindingAdapter("hideImage")
fun hideImage(view: ImageView, value: String?) {
    view.isVisible = value != "0"
}

