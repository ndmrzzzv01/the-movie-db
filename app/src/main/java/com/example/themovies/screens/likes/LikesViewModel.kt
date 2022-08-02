package com.example.themovies.screens.likes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themovies.network.data.*
import com.example.themovies.screens.BaseListViewModel
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
            networkList.forEach { type ->
                when (type) {
                    is Movie -> list.add(MediaItemType(type.id, type.posterPath, type.name, type = 0))
                    is TV -> list.add(MediaItemType(type.id, type.posterPath, type.name ?: "", type = 1))
                    is Person -> list.add(MediaItemType(type.id, type.posterPath, type.name ?: "", type = 2))
                    else -> {}
                }
            }
            likes.value = list
        }
    }


}