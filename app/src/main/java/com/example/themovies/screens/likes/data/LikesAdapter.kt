package com.example.themovies.screens.likes.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.databinding.ListItemBinding
import com.example.themovies.network.data.*
import com.example.themovies.screens.movie.data.MovieHolder
import com.example.themovies.screens.people.data.PeopleHolder
import com.example.themovies.screens.tv.data.TvHolder

class LikesAdapter(
    var list: List<RecordType?>,
    private val recordClick: RecordClick?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_MOVIE_ITEM = 0
        const val TYPE_TV_ITEM = 1
        const val TYPE_PEOPLE_ITEM = 2
    }


    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is Movie -> TYPE_MOVIE_ITEM
            is TV -> TYPE_TV_ITEM
            else -> TYPE_PEOPLE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_MOVIE_ITEM -> MovieHolder(
                ListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            TYPE_TV_ITEM -> TvHolder(
                ListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> PeopleHolder(
                ListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieHolder -> {
                val movie = list[position] as Movie
                holder.bind(movie)
                holder.itemView.setOnClickListener {
                    recordClick?.onRecordClickListener(movie.id ?: 0, Record.Movie)
                }
            }
            is TvHolder -> {
                val tv = list[position] as TV
                holder.bind(tv)
                holder.itemView.setOnClickListener {
                    recordClick?.onRecordClickListener(tv.id ?: 0, Record.TV)
                }
            }
            is PeopleHolder -> {
                val person = list[position] as Person
                holder.bind(person)
                holder.itemView.setOnClickListener {
                    recordClick?.onRecordClickListener(person.id ?: 0, Record.People)
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size


}