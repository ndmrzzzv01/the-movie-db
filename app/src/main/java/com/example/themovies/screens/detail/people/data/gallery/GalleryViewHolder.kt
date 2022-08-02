package com.example.themovies.screens.detail.people.data.gallery

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.databinding.ListInnerItemBinding
import com.example.themovies.network.data.Gallery

class GalleryViewHolder(val binding: ListInnerItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(gallery: Gallery) {
        binding.apply {
            tvName.visibility = View.GONE
            tvReleaseDate.visibility = View.GONE
            imageUrl = gallery.path
        }
    }

}