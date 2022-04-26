package com.example.themovies.screens.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.themovies.data.paging.MoviePagingSource
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val movieRepository = MovieRepository()

    val flow = Pager(PagingConfig(20)) {
        MoviePagingSource(movieRepository)
    }.flow.cachedIn(viewModelScope)
}