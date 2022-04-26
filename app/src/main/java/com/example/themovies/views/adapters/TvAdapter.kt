package com.example.themovies.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.themovies.data.TV
import com.example.themovies.databinding.ListItemBinding
import com.example.themovies.views.holders.TvHolder

class TvAdapter : PagingDataAdapter<TV, TvHolder>(MovieDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvHolder {
        return TvHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TvHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class MovieDiffCallBack : DiffUtil.ItemCallback<TV>() {
        override fun areItemsTheSame(oldItem: TV, newItem: TV): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TV, newItem: TV): Boolean {
            return oldItem == newItem
        }
    }
}