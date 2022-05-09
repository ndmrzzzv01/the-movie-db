package com.example.themovies.screens.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.themovies.data.FakeAd
import com.example.themovies.data.RecordType
import com.example.themovies.data.Movie
import com.example.themovies.data.paging.TheMovieDBPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private var listOfMovie: List<Movie> = mutableListOf()

    val flow = Pager(PagingConfig(20)) {
        TheMovieDBPagingSource { page ->
            withContext(Dispatchers.IO) {
                listOfMovie = movieRepository.getMovies(page)
                val fullList = listOfMovie.toMutableList<RecordType>()
                fullList.add(13, FakeAd("Your ad could be here №$page!"))
                return@withContext fullList
            }
        }
    }.flow.cachedIn(viewModelScope)

}