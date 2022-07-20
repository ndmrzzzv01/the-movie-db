package com.example.themovies.screens.detail.people.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.databinding.ItemPeopleBinding
import com.example.themovies.network.data.Movie
import com.example.themovies.network.data.RecordType
import com.example.themovies.network.data.TV
import com.example.themovies.screens.RecordAdapter

class KnownForAdapter(var list: List<RecordType?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is Movie -> RecordAdapter.TYPE_MOVIE_ITEM
            else -> RecordAdapter.TYPE_TV_ITEM
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            RecordAdapter.TYPE_MOVIE_ITEM -> MovieForKnownPeopleViewHolder(
                ItemPeopleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
            else -> TvForKnownPeopleViewHolder(
                ItemPeopleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieForKnownPeopleViewHolder -> {
                val movie = list[position] as Movie
                holder.bind(movie)
            }
            is TvForKnownPeopleViewHolder -> {
                val tv = list[position] as TV
                holder.bind(tv)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateList(listMovie: List<RecordType?>) {
        list = listMovie
        notifyDataSetChanged()
    }

}