package com.example.themovies.screens.people.data

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.themovies.R
import com.example.themovies.databinding.ListItemBinding
import com.example.themovies.network.data.Person
import com.example.themovies.network.repositories.ConfigurationRepository

class PeopleHolder(var binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(person: Person) {
        binding.tvTitle.text = person.name
        Glide
            .with(itemView.context)
            .load("${ConfigurationRepository.URL}${ConfigurationRepository.sizeOfPoster}${person.profilePath}")
            .apply(
                RequestOptions().placeholder(R.drawable.funnyunicorn)
                    .error(R.drawable.sadunicorn).centerCrop()
            )
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.image)

    }

}