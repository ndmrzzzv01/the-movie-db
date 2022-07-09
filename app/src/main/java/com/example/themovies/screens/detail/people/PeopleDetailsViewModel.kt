package com.example.themovies.screens.detail.people

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themovies.network.data.People
import com.example.themovies.database.data.Like
import com.example.themovies.screens.likes.repositories.LikesRepositoryDatabase
import com.example.themovies.screens.people.PeopleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleDetailsViewModel @Inject constructor(
    private val peopleRepository: PeopleRepository,
    private val likesRepositoryDatabase: LikesRepositoryDatabase
) : ViewModel() {

    val people = MutableLiveData<People>()
    val isLiked = MutableLiveData<Boolean>()

    fun getPeople(peopleId: Int?) {
        viewModelScope.launch {
            val itemPeople = peopleRepository.getPeople(peopleId)
            people.value = itemPeople!!
        }
    }

    fun isLiked(id: Int) {
        viewModelScope.launch {
            isLiked.value = likesRepositoryDatabase.isLiked(id)
        }
    }

    fun insertRecord(like: Like) {
        viewModelScope.launch {
            likesRepositoryDatabase.insertRecordToDatabase(like)
        }
    }

    fun deleteRecord(id: Int) {
        viewModelScope.launch {
            likesRepositoryDatabase.deleteRecordFromDatabase(id)
        }
    }
}