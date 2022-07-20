package com.example.themovies.screens.tv.data

import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.databinding.ListItemSeasonsBinding
import com.example.themovies.network.data.Season

class TvSeasonHolder(private val binding: ListItemSeasonsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(season: Season) {
        binding.apply {
            imageUrl = season.posterPath
            nameSeason = season.name
            releaseDate = season.airDate
        }
    }

}