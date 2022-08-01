package com.example.themovies.screens

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.themovies.network.data.MediaItemType
import com.example.themovies.network.data.RecordClick

class RecordAdapter(
    private val recordClick: RecordClick
) : PagingDataAdapter<MediaItemType, RecordViewHolder>(RecordTypeDiffUtil()) {

    companion object {
        const val TYPE_MOVIE_ITEM = 1
        const val TYPE_TV_ITEM = 2
        const val TYPE_PEOPLE_ITEM = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder.setBindingAndHandler(parent)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(getItem(position) as MediaItemType)
    }


    class RecordTypeDiffUtil : DiffUtil.ItemCallback<MediaItemType>() {
        override fun areItemsTheSame(oldRecord: MediaItemType, newRecord: MediaItemType): Boolean {
            return oldRecord == newRecord

        }

        override fun areContentsTheSame(
            oldRecord: MediaItemType,
            newRecord: MediaItemType
        ): Boolean {
            return oldRecord == newRecord
        }
    }

}

