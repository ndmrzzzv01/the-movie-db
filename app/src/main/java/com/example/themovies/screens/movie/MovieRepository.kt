package com.example.themovies.screens.movie

import com.example.themovies.api.ConfigurationApi
import com.example.themovies.api.MovieApi
import com.example.themovies.data.Movie
import com.example.themovies.network.ConfigurationRepository
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class MovieRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val configurationApi: ConfigurationApi
) {

    companion object {
        const val URL = "https://image.tmdb.org/t/p/"
    }

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

    private suspend fun checkConfiguration() {
        if (!ConfigurationRepository.isConfigurationDownloaded()) {
            ConfigurationRepository.downloadConfiguration(configurationApi)
        }
    }

}