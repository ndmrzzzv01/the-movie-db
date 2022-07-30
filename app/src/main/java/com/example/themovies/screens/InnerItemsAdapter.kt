package com.example.themovies.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.databinding.ListInnerItemBinding
import com.example.themovies.network.data.Actor
import com.example.themovies.network.data.RecordType
import com.example.themovies.network.data.Season
import com.example.themovies.screens.movie.data.ActorHolder
import com.example.themovies.screens.tv.data.TvSeasonHolder

class InnerItemsAdapter(private val items: List<RecordType>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_ACTOR_ITEM = 0
        const val TYPE_SEASON_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Actor -> TYPE_ACTOR_ITEM
            else -> TYPE_SEASON_ITEM
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TYPE_ACTOR_ITEM -> ActorHolder(
                ListInnerItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> TvSeasonHolder(
                ListInnerItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ActorHolder -> holder.bind(items[position] as Actor)
            is TvSeasonHolder -> holder.bind(items[position] as Season)
        }
    }

    override fun getItemCount(): Int = items.size

}