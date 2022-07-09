package com.example.themovies.screens.movie.data

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.themovies.R
import com.example.themovies.databinding.ListItemBinding
import com.example.themovies.network.data.Movie
import com.example.themovies.network.repositories.ConfigurationRepository

class MovieHolder(private val binding: ListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.tvTitle.text = movie.title
        if (movie.posterPath != null) {
            Glide
                .with(itemView.context)
                .load("${ConfigurationRepository.URL}${ConfigurationRepository.sizeOfPoster}${movie.posterPath}")
                .apply(
                    RequestOptions().placeholder(R.drawable.funnyunicorn).error(R.drawable.sadunicorn).centerCrop()
                )
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.image)
        }
    }

}