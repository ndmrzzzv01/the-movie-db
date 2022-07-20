package com.example.themovies.screens.movie.data

import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.databinding.ListItemBinding
import com.example.themovies.network.data.Movie

class MovieHolder(private val binding: ListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.apply {
            imageUrl = movie.posterPath
            title = movie.title
        }
    }

}