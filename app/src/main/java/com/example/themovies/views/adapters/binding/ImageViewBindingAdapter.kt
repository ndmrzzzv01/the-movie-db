package com.example.themovies.views.adapters.binding

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.themovies.network.ConfigurationRepository
import com.example.themovies.screens.movie.MovieRepository

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, liveUrl: String?) {

    if (liveUrl != "") {
        Glide
            .with(view)
            .load("${MovieRepository.URL}${ConfigurationRepository.sizeOfPoster}${liveUrl}")
            .into(view)
    } else {
        view.background = ColorDrawable(Color.BLACK)
    }

}
