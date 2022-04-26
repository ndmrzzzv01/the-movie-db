package com.example.themovies.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.data.FakeAd
import com.example.themovies.data.Movie
import com.example.themovies.databinding.ListItemBinding
import com.example.themovies.databinding.ViewFakeItemBinding
import com.example.themovies.screens.movie.MainFragment
import com.example.themovies.views.holders.FakeAdHolder
import com.example.themovies.views.holders.MovieHolder

enum class ItemType(val number: Int) {
    TYPE_MOVIE_ITEM(0),
    TYPE_FAKE_ITEM(1)
}

class MovieAdapter(
    private val onMovieItemClickListener: MainFragment.OnMovieItemClickListener
) : PagingDataAdapter<Any, RecyclerView.ViewHolder>(AnyDiffUtil()) {

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item is FakeAd) {
            ItemType.TYPE_FAKE_ITEM.number
        } else {
            ItemType.TYPE_MOVIE_ITEM.number
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ItemType.TYPE_FAKE_ITEM.number -> {
                return FakeAdHolder(
                    ViewFakeItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                return MovieHolder(
                    ListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FakeAdHolder -> {
                holder.bind(getItem(position) as FakeAd)
            }
            is MovieHolder -> {
                val movie = getItem(position) as Movie
                movie.let { movieItem ->
                    holder.bind(movieItem)
                    holder.itemView.setOnClickListener {
                        onMovieItemClickListener.onMovieClick(movieItem.id ?: 0)
                    }
                }
            }
        }
    }


    class AnyDiffUtil : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if (oldItem is FakeAd && newItem is FakeAd) {
                oldItem == newItem
            } else if (oldItem is Movie && newItem is Movie) {
                oldItem.id == newItem.id
            } else {
                false
            }
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if (oldItem is FakeAd && newItem is FakeAd) {
                oldItem == newItem
            } else if (oldItem is Movie && newItem is Movie) {
                oldItem == newItem
            } else {
                false
            }
        }

    }
}
