package com.example.themovies.views.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.themovies.network.MediaItemTypeActionHandler
import com.example.themovies.network.data.MediaItemType
import com.example.themovies.views.viewholders.RecordViewHolder

class RecordAdapter(
    private val actionHandler: MediaItemTypeActionHandler
) : PagingDataAdapter<MediaItemType, RecordViewHolder>(RecordTypeDiffUtil()) {

    companion object {
        const val TYPE_MOVIE_ITEM = 1
        const val TYPE_TV_ITEM = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder.setBindingAndHandler(parent, actionHandler)
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

