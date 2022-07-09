package com.example.themovies.screens.detail.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themovies.network.data.Movie
import com.example.themovies.database.data.Like
import com.example.themovies.screens.likes.repositories.LikesRepositoryDatabase
import com.example.themovies.screens.movie.popular.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val likesRepositoryDatabase: LikesRepositoryDatabase
) : ViewModel() {

    val movie = MutableLiveData<Movie>()
    val isLiked = MutableLiveData<Boolean>()

    fun getMovie(movieId: Int?) {
        viewModelScope.launch {
            val itemMovie = movieRepository.getMovie(movieId)
            movie.value = itemMovie!!
        }
    }

    fun isLiked(id: Int) {
        viewModelScope.launch {
            isLiked.value = likesRepositoryDatabase.isLiked(id)
        }
    }

    fun insertRecord(like: Like) {
        viewModelScope.launch {
            likesRepositoryDatabase.insertRecordToDatabase(like)
        }
    }

    fun deleteRecord(id: Int) {
        viewModelScope.launch {
            likesRepositoryDatabase.deleteRecordFromDatabase(id)
        }
    }

}