package com.example.themovies.screens.detail.people.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.databinding.ItemPeopleBinding
import com.example.themovies.network.data.Movie

class KnownForAdapter(var list: List<Movie>) :
    RecyclerView.Adapter<KnownForViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KnownForViewHolder {
        return KnownForViewHolder(
            ItemPeopleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: KnownForViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun updateList(listMovie: List<Movie>) {
        list = listMovie
        notifyDataSetChanged()
    }

}