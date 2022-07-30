package com.example.themovies.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.databinding.ViewHolderLoadStateBinding

class ListLoadStateAdapter : LoadStateAdapter<LoadStateViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            ViewHolderLoadStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}

class LoadStateViewHolder(
    private val binding: ViewHolderLoadStateBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        binding.apply {
            when (loadState) {
                is LoadState.Loading -> {
                    progressBar.isVisible = true
                }
                is LoadState.Error -> {
                    progressBar.isVisible = false
                    btnRetryAgain.isVisible = true
                    tvErrorMessage.isVisible = true
                }
                else -> {}
            }
        }
    }

}