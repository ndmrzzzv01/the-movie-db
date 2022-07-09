package com.example.themovies.screens.tv.data

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.themovies.R
import com.example.themovies.network.data.TV
import com.example.themovies.databinding.ListItemBinding
import com.example.themovies.network.repositories.ConfigurationRepository

class TvHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(tv: TV) {
        binding.tvTitle.text = tv.name
        if (tv.posterPath != null) {
            Glide
                .with(itemView.context)
                .load("${ConfigurationRepository.URL}${ConfigurationRepository.sizeOfPoster}${tv.posterPath}")
                .apply(
                    RequestOptions().placeholder(R.drawable.funnyunicorn).error(R.drawable.sadunicorn).centerCrop()
                )
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.image)
        }
    }
}