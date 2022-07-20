package com.example.themovies.screens.people.data

import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.databinding.ListItemBinding
import com.example.themovies.network.data.Person

class PeopleHolder(var binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(person: Person) {
        binding.apply {
            imageUrl = person.profilePath
            title = person.name
        }

    }

}