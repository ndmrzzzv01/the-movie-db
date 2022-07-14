package com.example.themovies.screens.movie.popular

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.themovies.R
import com.example.themovies.network.data.FakeAd
import com.example.themovies.network.data.Movie
import com.example.themovies.network.data.RecordType
import com.example.themovies.paging.TheMovieDBPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    @ApplicationContext context: Context
) : ViewModel() {

    private var listOfMovie: List<Movie> = mutableListOf()
    private val resources = context.resources

    val flow = Pager(PagingConfig(20)) {
        TheMovieDBPagingSource { page ->
            withContext(Dispatchers.IO) {
                listOfMovie = movieRepository.getMovies(page)
                val fullList = listOfMovie.toMutableList<RecordType>()
                fullList.add(
                    13,
                    FakeAd(String.format(resources.getString(R.string.advertisement), page))
                )
                return@withContext fullList
            }
        }
    }.flow.cachedIn(viewModelScope)

}