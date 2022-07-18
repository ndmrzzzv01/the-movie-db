package com.example.themovies.screens.detail.people

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themovies.database.data.Like
import com.example.themovies.network.data.Movie
import com.example.themovies.network.data.Person
import com.example.themovies.screens.likes.repositories.LikesRepositoryDatabase
import com.example.themovies.screens.people.PeopleFragment
import com.example.themovies.screens.people.PeopleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PeopleDetailsViewModel @Inject constructor(
    private val peopleRepository: PeopleRepository,
    private val likesRepositoryDatabase: LikesRepositoryDatabase
) : ViewModel() {

    val person = MutableLiveData<Person>()
    val movieForKnownPerson = MutableLiveData<List<Movie>>()
    val isLiked = MutableLiveData<Boolean>()

    fun getPeople(peopleId: Int?) {
        viewModelScope.launch {
            val itemPeople = peopleRepository.getPeople(peopleId)
            person.value = itemPeople!!
        }
    }

    fun getMovieForKnownPerson(customParameters: PeopleFragment.CustomParameters?) {
        viewModelScope.launch {
            try {
                val list = mutableListOf<Movie>()
                customParameters?.id?.forEach {
                    val person = peopleRepository.getMovieForKnownPerson(it.id)
                    person?.let { movie ->
                        list.add(movie)
                    }
                }
                movieForKnownPerson.value = list
                Timber.d(list.toString())
            } catch (exception: Exception) {
                Timber.e(exception)
            }
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