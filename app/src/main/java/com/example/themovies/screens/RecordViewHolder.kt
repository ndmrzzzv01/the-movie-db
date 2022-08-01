package com.example.themovies.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.databinding.ListItemBinding
import com.example.themovies.network.data.MediaItemType

class RecordViewHolder(private val binding: ListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun setBindingAndHandler(parent: ViewGroup): RecordViewHolder {
            val binding = ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
//            TODO: тут надо назначить клики
            return RecordViewHolder(binding)
        }
    }

    fun bind(type: MediaItemType) {
        binding.type = type
    }

}