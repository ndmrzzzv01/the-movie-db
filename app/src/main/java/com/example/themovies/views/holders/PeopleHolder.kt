package com.example.themovies.views.holders

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themovies.data.People
import com.example.themovies.databinding.ListItemBinding
import com.example.themovies.network.ConfigurationRepository
import com.example.themovies.screens.movie.popular.MovieRepository

class PeopleHolder(var binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(people: People) {
        binding.tvTitle.text = people.name
        if (people.profilePath != null) {
            Glide
                .with(itemView.context)
                .load("${MovieRepository.URL}${ConfigurationRepository.sizeOfPoster}${people.profilePath}")
                .into(binding.image)
        } else {
            binding.image.background = ColorDrawable(Color.BLACK)
        }
    }

}