package com.example.themovies.views.holders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themovies.data.Movie
import com.example.themovies.databinding.ListItemMovieBinding
import com.example.themovies.network.MovieRepository
import com.example.themovies.screens.list.MainFragment

class MovieHolder(private val binding: ListItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Movie) {
        binding.tvTitleOfTheMovie.text = movie.title
        Glide
            .with(itemView.context)
            .load("${MovieRepository.URL}${MainFragment.sizeOfPoster}${movie.posterPath}")
            .into(binding.imageMovie)
    }
}