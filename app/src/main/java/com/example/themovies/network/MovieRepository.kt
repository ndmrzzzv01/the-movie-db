package com.example.themovies.network

import com.example.themovies.api.ConfigurationApi
import com.example.themovies.api.MovieApi
import com.example.themovies.data.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepository {

    companion object {
        const val URL = "https://image.tmdb.org/t/p/"
        const val TAG = "MoviesRepository"
    }

    private val movieApi: MovieApi
    private val configurationApi: ConfigurationApi

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(MovieInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        movieApi = retrofit.create(MovieApi::class.java)
        configurationApi = retrofit.create(ConfigurationApi::class.java)
    }

    suspend fun getMovies(page: Int = 1): List<Movie> = withContext(Dispatchers.IO) {
        if (page >= 2) {
            delay(2000L)
        }
        movieApi.getPopularMovies(page)?.results ?: mutableListOf()
    }

    suspend fun getConfiguration(): List<String> = withContext(Dispatchers.IO) {
        configurationApi.getConfiguration()?.imagesSize?.posterSizes ?: emptyList()
    }

    suspend fun getMovie(movieId: Int?): Movie? = withContext(Dispatchers.IO) {
        movieApi.getMovie(movieId)
    }

}