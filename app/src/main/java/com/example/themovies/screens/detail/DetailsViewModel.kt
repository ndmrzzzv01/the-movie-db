package com.example.themovies.screens.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themovies.data.Movie
import com.example.themovies.network.MovieRepository
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    private val movieRepository = MovieRepository()
    val movie = MutableLiveData<Movie>()

    fun getMovie(movieId: Int?) {
        viewModelScope.launch {
            val itemMovie = movieRepository.getMovie(movieId)
            movie.value = itemMovie
        }
    }
}