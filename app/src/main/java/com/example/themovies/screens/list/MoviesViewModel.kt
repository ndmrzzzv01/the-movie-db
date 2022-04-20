package com.example.themovies.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.themovies.data.paging.MoviesPagingSource
import com.example.themovies.network.MoviesRepository
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {
    private val moviesRepository = MoviesRepository()

    fun downloadConfiguration() {
        viewModelScope.launch {
            val sizes = moviesRepository.getConfiguration()
            MainFragment.sizeOfPoster = sizes[sizes.size - 2]
        }
    }

    val flow = Pager(PagingConfig(20)) {
        MoviesPagingSource(moviesRepository)
    }.flow.cachedIn(viewModelScope)
}