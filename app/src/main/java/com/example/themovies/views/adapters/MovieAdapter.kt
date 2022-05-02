package com.example.themovies.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.data.*
import com.example.themovies.databinding.ListItemBinding
import com.example.themovies.databinding.ViewFakeItemBinding
import com.example.themovies.screens.movie.MovieFragment
import com.example.themovies.views.holders.FakeAdHolder
import com.example.themovies.views.holders.MovieHolder
import com.example.themovies.views.holders.PeopleHolder
import com.example.themovies.views.holders.TvHolder

class MovieAdapter(
    private val onMovieItemClickListener: MovieFragment.OnMovieItemClickListener
) : PagingDataAdapter<ItemType, RecyclerView.ViewHolder>(AnyDiffUtil()) {

    companion object {
        const val TYPE_FAKE_ITEM = 0
        const val TYPE_MOVIE_ITEM = 1
        const val TYPE_TV_ITEM = 2
        const val TYPE_PEOPLE_ITEM = 3
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is FakeAd -> TYPE_FAKE_ITEM
            is Movie -> TYPE_MOVIE_ITEM
            is TV -> TYPE_TV_ITEM
            else -> TYPE_PEOPLE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_FAKE_ITEM -> {
                return FakeAdHolder(
                    ViewFakeItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            TYPE_MOVIE_ITEM -> {
                return MovieHolder(
                    setListItemBinding(parent)
                )
            }
            TYPE_TV_ITEM -> {
                return TvHolder(
                    setListItemBinding(parent)
                )
            }
            else ->
                return PeopleHolder(
                    setListItemBinding(parent)
                )
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
            is TvHolder -> {
                holder.bind(getItem(position) as TV)
            }
            is PeopleHolder -> {
                holder.bind(getItem(position) as People)
            }
        }
    }

    private fun setListItemBinding(parent: ViewGroup): ListItemBinding {
        return ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    class AnyDiffUtil : DiffUtil.ItemCallback<ItemType>() {
        override fun areItemsTheSame(oldItem: ItemType, newItem: ItemType): Boolean {
            return when {
                oldItem is FakeAd && newItem is FakeAd -> {
                    oldItem == newItem
                }
                oldItem is Movie && newItem is Movie -> {
                    oldItem.id == newItem.id
                }
                oldItem is TV && newItem is TV -> {
                    oldItem.id == newItem.id
                }
                oldItem is People && newItem is People -> {
                    oldItem.id == newItem.id
                }
                else -> {
                    false
                }
            }
        }

        override fun areContentsTheSame(oldItem: ItemType, newItem: ItemType): Boolean {
            return when {
                oldItem is FakeAd && newItem is FakeAd -> {
                    oldItem == newItem
                }
                oldItem is Movie && newItem is Movie -> {
                    oldItem == newItem
                }
                oldItem is TV && newItem is TV -> {
                    oldItem == newItem
                }
                oldItem is People && newItem is People -> {
                    oldItem == newItem
                }
                else -> {
                    false
                }
            }
        }

    }
}
