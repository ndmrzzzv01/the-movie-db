package com.example.themovies.screens.movie

import com.example.themovies.api.ConfigurationApi
import com.example.themovies.api.MovieApi
import com.example.themovies.data.Movie
import com.example.themovies.network.ConfigurationRepository
import com.example.themovies.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MovieRepository {

    companion object {
        const val URL = "https://image.tmdb.org/t/p/"
    }

    private val movieApi = NetworkUtils.createRetrofit().create(MovieApi::class.java)
    private val configurationApi = NetworkUtils.createRetrofit().create(ConfigurationApi::class.java)

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