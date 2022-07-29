package com.example.themovies.screens.movie.popular

import com.example.themovies.api.MovieApi
import com.example.themovies.network.data.Actor
import com.example.themovies.network.data.Movie
import com.example.themovies.network.repositories.ConfigurationRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class MovieRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val configurationRepository: ConfigurationRepository
) {

    suspend fun getMovies(page: Int = 1): List<Movie> = withContext(Dispatchers.IO) {
        checkConfiguration()
        if (page >= 2) {
            delay(2000L)
        }
        movieApi.getPopularMovies(page)?.results ?: mutableListOf()
    }

    suspend fun getMovie(movieId: Int?): Movie? = withContext(Dispatchers.IO) {
        checkConfiguration()
        movieApi.getMovie(movieId)
    }

    suspend fun getCredits(movieId: Int?): List<Actor> = withContext(Dispatchers.IO) {
        checkConfiguration()
        movieApi.getCredits(movieId).cast ?: mutableListOf()
    }

    private suspend fun checkConfiguration() {
        if (!configurationRepository.isConfigurationDownloaded()) {
            configurationRepository.downloadConfiguration()
        }
    }

}