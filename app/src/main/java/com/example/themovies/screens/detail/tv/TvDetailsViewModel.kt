package com.example.themovies.screens.detail.tv

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themovies.data.TV
import com.example.themovies.screens.tv.TVRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvDetailsViewModel @Inject constructor(
    private val tvRepository: TVRepository
) : ViewModel() {

    val tv = MutableLiveData<TV>()

    fun getTv(tvId: Int) {
        viewModelScope.launch {
            val itemTv = tvRepository.getTv(tvId)
            tv.value = itemTv!!
        }
    }

}