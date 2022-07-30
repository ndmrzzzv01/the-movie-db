package com.example.themovies.screens.movie.data

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.databinding.ListInnerItemBinding
import com.example.themovies.network.data.Actor

class ActorHolder(val binding: ListInnerItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(actor: Actor) {
        binding.apply {
            tvReleaseDate.visibility = View.GONE
            imageUrl = actor.profilePath
            name = actor.name
        }
    }

}