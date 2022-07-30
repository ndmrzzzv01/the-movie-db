package com.example.themovies.screens.tv.data

import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.databinding.ListInnerItemBinding
import com.example.themovies.network.data.Season

class TvSeasonHolder(private val binding: ListInnerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(season: Season) {
        binding.apply {
            imageUrl = season.posterPath
            name = season.name
            releaseDate = season.airDate
            tvName.minLines = 1
        }
    }

}