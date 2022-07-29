package com.example.themovies.screens.movie.data

import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.databinding.ListItemCreditBinding
import com.example.themovies.network.data.Actor

class ActorHolder(val binding: ListItemCreditBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(actor: Actor) {
        binding.apply {
            imageUrl = actor.profilePath
            name = actor.name
        }
    }

}