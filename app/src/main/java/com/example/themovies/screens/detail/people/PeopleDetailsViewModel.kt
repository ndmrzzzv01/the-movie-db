package com.example.themovies.screens.detail.people

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themovies.database.data.Like
import com.example.themovies.network.data.Person
import com.example.themovies.network.data.RecordType
import com.example.themovies.screens.likes.repositories.LikesRepositoryDatabase
import com.example.themovies.screens.movie.popular.MovieRepository
import com.example.themovies.screens.people.PeopleFragment
import com.example.themovies.screens.people.PeopleRepository
import com.example.themovies.screens.tv.TVRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PeopleDetailsViewModel @Inject constructor(
    private val peopleRepository: PeopleRepository,
    private val movieRepository: MovieRepository,
    private val tvRepository: TVRepository,
    private val likesRepositoryDatabase: LikesRepositoryDatabase
) : ViewModel() {

    val person = MutableLiveData<Person>()
    val movieForKnownPerson = MutableLiveData<List<RecordType?>>()
    val isLiked = MutableLiveData<Boolean>()

    fun getPeople(peopleId: Int?) {
        viewModelScope.launch {
            val itemPeople = peopleRepository.getPeople(peopleId)
            person.value = itemPeople!!
        }
    }

    fun getMovieOrTvForKnownPerson(customParameters: PeopleFragment.CustomParameters?) {
        viewModelScope.launch {
            try {
                val list = mutableListOf<RecordType?>()
                var query: RecordType? = null
                customParameters?.customParameters?.forEach {
                    query = if (it.type == "movie") {
                        movieRepository.getMovie(it.id)
                    } else {
                        tvRepository.getTv(it.id ?: 0)
                    }
                    list.add(query)
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