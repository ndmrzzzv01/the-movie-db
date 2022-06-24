package com.example.themovies.screens.people

import com.example.themovies.api.ConfigurationApi
import com.example.themovies.api.PeopleApi
import com.example.themovies.data.People
import com.example.themovies.network.ConfigurationRepository
import com.example.themovies.utils.NetworkUtils
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class PeopleRepository @Inject constructor(
    private val peopleApi: PeopleApi,
    private val configurationApi: ConfigurationApi
) {

    suspend fun getPopularPeople(page: Int = 1): List<People> = withContext(Dispatchers.IO) {
        checkConfiguration()
        if (page >= 2) {
            delay(2000L)
        }
        peopleApi.getPopularPeople(page)?.results ?: mutableListOf()
    }

    suspend fun getPeople(peopleId: Int?): People? = withContext(Dispatchers.IO) {
        checkConfiguration()
        peopleApi.getPeople(peopleId)
    }

    private suspend fun checkConfiguration() {
        if (!ConfigurationRepository.isConfigurationDownloaded()) {
            ConfigurationRepository.downloadConfiguration(configurationApi)
        }
    }

}