package com.example.themovies.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.themovies.data.paging.MoviePagingSource
import com.example.themovies.network.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val movieRepository = MovieRepository()

    fun downloadConfiguration() {
        viewModelScope.launch {
            val sizes = movieRepository.getConfiguration()
            MainFragment.sizeOfPoster = sizes[sizes.size - 2]
        }
    }

    val flow = Pager(PagingConfig(20)) {
        MoviePagingSource(movieRepository)
    }.flow.cachedIn(viewModelScope)
}