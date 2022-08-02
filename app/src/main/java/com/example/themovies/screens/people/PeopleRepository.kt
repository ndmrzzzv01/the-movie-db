package com.example.themovies.screens.people

import com.example.themovies.api.PeopleApi
import com.example.themovies.network.data.Gallery
import com.example.themovies.network.data.Movie
import com.example.themovies.network.data.Person
import com.example.themovies.network.data.TV
import com.example.themovies.network.repositories.ConfigurationRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class PeopleRepository @Inject constructor(
    private val peopleApi: PeopleApi,
    private val configurationRepository: ConfigurationRepository
) {

    suspend fun getPopularPeople(page: Int = 1): List<Person> = withContext(Dispatchers.IO) {
        checkConfiguration()
        if (page >= 2) {
            delay(2000L)
        }
        peopleApi.getPopularPeople(page)?.results ?: listOf()
    }

    suspend fun getPeople(peopleId: Int?): Person? = withContext(Dispatchers.IO) {
        checkConfiguration()
        peopleApi.getPeople(peopleId)
    }

    suspend fun getCastOfMovie(movieId: Int?): List<Movie> = withContext(Dispatchers.IO) {
        checkConfiguration()
        peopleApi.getCastForMovie(movieId).castOfMovie ?: listOf()
    }

    suspend fun getCastOfTv(tvId: Int?): List<TV> = withContext(Dispatchers.IO) {
        checkConfiguration()
        peopleApi.getCastForTv(tvId).castOfTv ?: listOf()
    }

    suspend fun getImages(personId: Int?): List<Gallery> = withContext(Dispatchers.IO) {
        checkConfiguration()
        peopleApi.getImages(personId).gallery ?: listOf()
    }

    private suspend fun checkConfiguration() {
        if (!configurationRepository.isConfigurationDownloaded()) {
            configurationRepository.downloadConfiguration()
        }
    }

}