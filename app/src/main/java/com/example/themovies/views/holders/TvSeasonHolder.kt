package com.example.themovies.views.holders

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themovies.data.Season
import com.example.themovies.databinding.ListItemSeasonsBinding
import com.example.themovies.network.ConfigurationRepository
import com.example.themovies.screens.movie.popular.MovieRepository

class TvSeasonHolder(private val binding: ListItemSeasonsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(season: Season) {
        binding.tvNameSeason.text = season.name
        if (season.posterPath != null) {
            Glide
                .with(itemView.context)
                .load("${ConfigurationRepository.URL}${ConfigurationRepository.sizeOfPoster}${season.posterPath}")
                .into(binding.imageSeason)
        } else {
            binding.imageSeason.background = ColorDrawable(Color.BLACK)
        }
        binding.tvReleaseSeason.text = season.airDate
    }

}