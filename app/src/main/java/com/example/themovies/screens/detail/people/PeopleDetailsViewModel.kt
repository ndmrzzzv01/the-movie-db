package com.example.themovies.screens.detail.people

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themovies.data.People
import com.example.themovies.screens.people.PeopleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
//class PeopleDetailsViewModel @Inject constructor(
//    private val peopleRepository: PeopleRepository
//) : ViewModel() {
class PeopleDetailsViewModel : ViewModel() {
    private val peopleRepository = PeopleRepository()
    val people = MutableLiveData<People>()

    fun getPeople(peopleId: Int?) {
        viewModelScope.launch {
            val itemPeople = peopleRepository.getPeople(peopleId)
            people.value = itemPeople!!
        }
    }
}