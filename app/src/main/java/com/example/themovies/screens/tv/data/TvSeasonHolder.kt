package com.example.themovies.screens.tv.data

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.themovies.R
import com.example.themovies.network.data.Season
import com.example.themovies.databinding.ListItemSeasonsBinding
import com.example.themovies.network.repositories.ConfigurationRepository

class TvSeasonHolder(private val binding: ListItemSeasonsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(season: Season) {
        binding.tvNameSeason.text = season.name
        if (season.posterPath != null) {
            Glide
                .with(itemView.context)
                .load("${ConfigurationRepository.URL}${ConfigurationRepository.sizeOfPoster}${season.posterPath}")
                .apply(
                    RequestOptions().placeholder(R.drawable.funnyunicorn).error(R.drawable.sadunicorn).centerCrop()
                )
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imageSeason)
        }
        binding.tvReleaseSeason.text = season.airDate
    }

}