package com.example.themovies.screens.detail.people

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themovies.database.data.Like
import com.example.themovies.network.data.Person
import com.example.themovies.network.data.RecordType
import com.example.themovies.screens.likes.repositories.LikesRepositoryDatabase
import com.example.themovies.screens.movie.popular.MovieRepository
import com.example.themovies.screens.people.PeopleRepository
import com.example.themovies.screens.tv.TVRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleDetailsViewModel @Inject constructor(
    private val peopleRepository: PeopleRepository,
    private val likesRepositoryDatabase: LikesRepositoryDatabase
) : ViewModel() {

    val person = MutableLiveData<Person>()
    val listOfMovieAndTV = MutableLiveData<List<RecordType?>>()
    val isLiked = MutableLiveData<Boolean>()

    private val list = mutableListOf<RecordType>()

    fun getPeople(peopleId: Int?) {
        viewModelScope.launch {
            val itemPeople = peopleRepository.getPeople(peopleId)
            person.value = itemPeople!!
        }
    }

    fun getCastOfMovie(movieId: Int?) {
        viewModelScope.launch {
            peopleRepository.getCastOfMovie(movieId).forEach {
                if ((it.popularity ?: 0.0) > 5.0) {
                    list.add(it)
                }
            }
            listOfMovieAndTV.value = list
        }
    }

    fun getCastOfTv(tvId: Int?) {
        viewModelScope.launch {
            peopleRepository.getCastOfTv(tvId).forEach {
                if ((it.popularity ?: 0.0) > 5.0) {
                    list.add(it)
                }
            }
            listOfMovieAndTV.value = list
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