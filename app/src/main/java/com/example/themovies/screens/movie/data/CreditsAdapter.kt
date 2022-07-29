package com.example.themovies.screens.movie.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.databinding.ListItemCreditBinding
import com.example.themovies.network.data.Actor

class ActorAdapter(private val actors: List<Actor>) : RecyclerView.Adapter<ActorHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorHolder = ActorHolder(
        ListItemCreditBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ActorHolder, position: Int) {
        holder.bind(actors[position])
    }

    override fun getItemCount(): Int = actors.size
}