package com.example.themovies.views.holders

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themovies.data.Movie
import com.example.themovies.databinding.ListItemBinding
import com.example.themovies.network.ConfigurationRepository
import com.example.themovies.screens.movie.popular.MovieRepository

class MovieHolder(private val binding: ListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.tvTitle.text = movie.title
        if (movie.posterPath != null) {
            Glide
                .with(itemView.context)
                .load("${MovieRepository.URL}${ConfigurationRepository.sizeOfPoster}${movie.posterPath}")
                .into(binding.image)
        } else {
            binding.image.background = ColorDrawable(Color.BLACK)
        }
    }

}