package com.example.themovies.screens.detail.tv

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themovies.data.Season
import com.example.themovies.data.TV
import com.example.themovies.network.responses.SeasonResponse
import com.example.themovies.screens.tv.TVRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvDetailsViewModel @Inject constructor(
    private val tvRepository: TVRepository
) : ViewModel() {

    val tv = MutableLiveData<TV?>()
    val season = MutableLiveData<SeasonResponse?>()

    fun getTv(tvId: Int) {
        viewModelScope.launch {
            val job1 = async { tvRepository.getTv(tvId) }
            val job2 = async { tvRepository.getTvSeason(tvId) }
            val itemTv = job1.await()
            val seasonItem = job2.await()

            tv.value = itemTv
            season.value = seasonItem
        }
    }


}