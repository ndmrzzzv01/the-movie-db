package com.example.themovies.screens.detail.people.data

import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.databinding.ItemPeopleBinding
import com.example.themovies.network.data.Movie

class MovieForKnownPeopleViewHolder(val binding: ItemPeopleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.apply {
            imageString = movie.backdropPath
            title = movie.name
            originalTitle = movie.originalName
            releaseDate = movie.releaseDate
            vote = movie.vote
            description = movie.description
        }
    }

}