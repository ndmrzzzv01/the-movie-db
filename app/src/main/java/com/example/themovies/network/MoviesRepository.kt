package com.example.themovies.network

import com.example.themovies.api.ConfigurationApi
import com.example.themovies.api.MoviesApi
import com.example.themovies.data.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesRepository {

    companion object {
        const val URL = "https://image.tmdb.org/t/p/"
        const val TAG = "MoviesRepository"
    }

    private val movieApi: MoviesApi
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

        movieApi = retrofit.create(MoviesApi::class.java)
        configurationApi = retrofit.create(ConfigurationApi::class.java)
    }

    suspend fun getMovies(page: Int = 1): List<Movie> = withContext(Dispatchers.IO) {
        delay(2000L)
        movieApi.getPopularMovies(page)?.results ?: mutableListOf()
    }

    suspend fun getConfiguration(): List<String> = withContext(Dispatchers.IO) {
        configurationApi.getConfiguration()?.imagesSize?.posterSizes ?: emptyList()
    }

}