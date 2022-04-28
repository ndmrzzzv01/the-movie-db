package com.example.themovies.views.holders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themovies.data.People
import com.example.themovies.databinding.ListItemBinding
import com.example.themovies.network.ConfigurationRepository
import com.example.themovies.screens.movie.MovieRepository

class PeopleHolder(var binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(people: People) {
        binding.tvTitle.text = people.name
        Glide
            .with(itemView.context)
            .load("${MovieRepository.URL}${ConfigurationRepository.sizeOfPoster}${people.profilePath}")
            .into(binding.image)
    }

}