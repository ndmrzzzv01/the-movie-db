package com.example.themovies.network.repositories

import com.example.themovies.api.ConfigurationApi
import kotlinx.coroutines.coroutineScope
import kotlin.math.max

object ConfigurationRepository {

    var sizeOfPoster: String? = null

    const val URL = "https://image.tmdb.org/t/p/"

    fun isConfigurationDownloaded() = sizeOfPoster != null

    suspend fun downloadConfiguration(configurationApi: ConfigurationApi) {
        coroutineScope {
            val list = configurationApi.getConfiguration()?.imagesSize?.posterSizes ?: emptyList()
            if (list.isNotEmpty()) {
                sizeOfPoster = list[max(list.size - 2, 0)]
            }
        }
    }

}