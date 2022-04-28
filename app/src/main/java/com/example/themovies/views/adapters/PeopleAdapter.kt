package com.example.themovies.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.data.People
import com.example.themovies.databinding.ListItemBinding
import com.example.themovies.views.holders.PeopleHolder

class PeopleAdapter(var people: List<People>): RecyclerView.Adapter<PeopleHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleHolder {
        return PeopleHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PeopleHolder, position: Int) {
        holder.bind(people[position])
    }

    override fun getItemCount(): Int = people.size

}