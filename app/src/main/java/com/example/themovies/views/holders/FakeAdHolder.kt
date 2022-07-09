package com.example.themovies.views.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.network.data.FakeAd
import com.example.themovies.databinding.ViewFakeItemBinding

class FakeAdHolder(private val binding: ViewFakeItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(fakeAd: FakeAd) {
        binding.tvFakeAd.text = fakeAd.name
    }

}




