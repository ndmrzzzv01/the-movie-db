package com.example.themovies.screens.likes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themovies.network.data.RecordType
import com.example.themovies.database.data.Like
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikesViewModel @Inject constructor(
    var likesCommonRepository: LikesCommonRepository
) : ViewModel() {

    val likes = MutableLiveData<MutableList<RecordType?>>()
    val like = MutableLiveData<Like>()

    fun getAllLikes() {
        viewModelScope.launch {
            val list = likesCommonRepository.getLikes()
            likes.value = list
        }
    }


}