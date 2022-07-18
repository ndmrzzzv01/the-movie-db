package com.example.themovies.screens.tv.data

import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.databinding.ListItemBinding
import com.example.themovies.network.data.TV

class TvHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(tv: TV) {
        binding.apply {
            imageUrl = tv.posterPath
            title = tv.name
        }
    }
}