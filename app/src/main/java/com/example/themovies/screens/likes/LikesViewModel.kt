package com.example.themovies.screens.likes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themovies.network.data.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikesViewModel @Inject constructor(
    var likesCommonRepository: LikesCommonRepository
) : ViewModel() {

    val likes = MutableLiveData<List<MediaItemType?>>()

    fun getAllLikes() {
        viewModelScope.launch {
            val networkList = likesCommonRepository.getLikes()
            val list = mutableListOf<MediaItemType>()
            networkList.forEach {
                when (it) {
                    is Movie -> list.add(MediaItemType(it.id, it.posterPath, it.name, type = Record.Movie))
                    is TV -> list.add(MediaItemType(it.id, it.posterPath, it.name ?: "", type = Record.TV))
                    is Person -> list.add(MediaItemType(it.id, it.posterPath, it.name ?: "", type = Record.People))
                }
            }
            likes.value = list
        }
    }


}