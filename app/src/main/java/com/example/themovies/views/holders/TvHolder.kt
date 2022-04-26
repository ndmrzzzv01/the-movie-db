package com.example.themovies.views.holders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themovies.data.TV
import com.example.themovies.databinding.ListItemBinding
import com.example.themovies.network.ConfigurationRepository
import com.example.themovies.screens.movie.MovieRepository

class TvHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(tv: TV) {
        binding.tvTitleOfTheMovie.text = tv.name
        Glide
            .with(itemView.context)
            .load("${MovieRepository.URL}${ConfigurationRepository.sizeOfPoster}${tv.posterPath}")
            .into(binding.image)
    }
}