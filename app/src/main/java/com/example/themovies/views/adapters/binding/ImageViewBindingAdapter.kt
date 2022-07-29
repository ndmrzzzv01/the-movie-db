package com.example.themovies.views.adapters.binding

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.themovies.R
import com.example.themovies.network.repositories.ConfigurationRepository

@BindingAdapter("loadImage")
fun ImageView.loadImage(liveUrl: String?) {
    if (liveUrl?.isNotEmpty() == true) {
        Glide
            .with(this)
            .load("${ConfigurationRepository.URL}${ConfigurationRepository.sizeOfPoster}${liveUrl}")
            .into(this)
    } else {
        this.background = ColorDrawable(Color.BLACK)
    }
}

@BindingAdapter("loadImageForMainLists")
fun ImageView.loadImageForMainLists(path: String?) {
    Glide
        .with(this)
        .load("${ConfigurationRepository.URL}${ConfigurationRepository.sizeOfPoster}${path}")
        .apply(
            RequestOptions().placeholder(R.drawable.funnyunicorn).error(R.drawable.sadunicorn)
                .centerCrop()
        )
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

@BindingAdapter("loadImageForItem")
fun ImageView.loadImageForItem(path: String?) {
    Glide
        .with(this)
        .load("${ConfigurationRepository.URL}${ConfigurationRepository.sizeOfPoster}${path}")
        .apply(RequestOptions().error(R.drawable.sadunicorn))
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

@BindingAdapter("hideImage")
fun ImageView.hideImage(value: String?) {
    this.isVisible = value != "0"
}

