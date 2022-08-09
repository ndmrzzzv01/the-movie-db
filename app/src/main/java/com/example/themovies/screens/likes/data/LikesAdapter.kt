package com.example.themovies.screens.likes.data

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.network.MediaItemTypeActionHandler
import com.example.themovies.network.data.MediaItemType
import com.example.themovies.views.viewholders.RecordViewHolder

class LikesAdapter(
    var list: List<MediaItemType?>,
    private val actionHandler: MediaItemTypeActionHandler
) : RecyclerView.Adapter<RecordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder.setBindingAndHandler(parent, actionHandler)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(list[position] as MediaItemType)
    }

    override fun getItemCount(): Int = list.size

    fun updateList(list: List<MediaItemType?>) {
        this.list = list
        notifyItemChanged(list.size - 1)
    }
}