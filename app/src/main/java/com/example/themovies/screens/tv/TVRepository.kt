package com.example.themovies.screens.tv

import com.example.themovies.api.ConfigurationApi
import com.example.themovies.api.TvApi
import com.example.themovies.network.data.TV
import com.example.themovies.network.repositories.ConfigurationRepository
import com.example.themovies.network.responses.SeasonResponse
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class TVRepository @Inject constructor(
    private val configurationApi: ConfigurationApi,
    private val tvApi: TvApi
) {

    suspend fun getPopularTV(page: Int = 1): List<TV> = withContext(Dispatchers.IO) {
        checkConfiguration()
        if (page >= 2) {
            delay(2000L)
        }
        tvApi.getPopularTV(page)?.results ?: mutableListOf()
    }

    suspend fun getTv(tvId: Int): TV? = withContext(Dispatchers.IO) {
        checkConfiguration()
        tvApi.getTV(tvId)
    }

    suspend fun getTvSeason(tvId: Int): SeasonResponse? = withContext(Dispatchers.IO) {
        checkConfiguration()
        tvApi.getTVSeason(tvId)
    }

    private suspend fun checkConfiguration() {
        if (!ConfigurationRepository.isConfigurationDownloaded()) {
            ConfigurationRepository.downloadConfiguration(configurationApi)
        }
    }
}