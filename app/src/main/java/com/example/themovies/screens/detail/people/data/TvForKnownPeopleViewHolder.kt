package com.example.themovies.screens.detail.people.data

import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.databinding.ItemPeopleBinding
import com.example.themovies.network.data.TV

class TvForKnownPeopleViewHolder(val binding: ItemPeopleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(tv: TV) {
        binding.apply {
            imageString = tv.backdropPath
            title = tv.name
            originalTitle = tv.originalName
            releaseDate = tv.releaseDate
            vote = tv.voteAverage.toString()
            description = tv.overview
        }
    }

}