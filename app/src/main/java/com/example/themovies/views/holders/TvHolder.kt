package com.example.themovies.views.holders

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themovies.data.TV
import com.example.themovies.databinding.ListItemBinding
import com.example.themovies.network.ConfigurationRepository
import com.example.themovies.screens.movie.popular.MovieRepository

class TvHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(tv: TV) {
        binding.tvTitle.text = tv.name
        if (tv.posterPath != null) {
            Glide
                .with(itemView.context)
                .load("${ConfigurationRepository.URL}${ConfigurationRepository.sizeOfPoster}${tv.posterPath}")
                .into(binding.image)
        } else {
            binding.image.background = ColorDrawable(Color.BLACK)
        }
    }
}