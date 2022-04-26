package com.example.themovies.screens.tv

import com.example.themovies.api.ConfigurationApi
import com.example.themovies.api.TVApi
import com.example.themovies.data.TV
import com.example.themovies.network.ConfigurationRepository
import com.example.themovies.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class TVRepository {

    private val tvApi = NetworkUtils.createRetrofit().create(TVApi::class.java)
    private val configurationApi = NetworkUtils.createRetrofit().create(ConfigurationApi::class.java)

    suspend fun getPopularTV(page: Int = 1): List<TV> = withContext(Dispatchers.IO) {
        checkConfiguration()
        if (page >= 2) {
            delay(2000L)
        }
        tvApi.getPopularTV(page)?.results ?: mutableListOf()
    }

    private suspend fun checkConfiguration() {
        if (!ConfigurationRepository.isConfigurationDownloaded()) {
            ConfigurationRepository.downloadConfiguration(configurationApi)
        }
    }
}