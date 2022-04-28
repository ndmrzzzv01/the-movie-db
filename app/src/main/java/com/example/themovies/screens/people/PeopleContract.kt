package com.example.themovies.screens.people

import com.example.themovies.data.People

interface PeopleContract {

    interface PeopleView {
        fun displayListOfPeople(list: List<People>)
        fun onFail()
    }

    interface PeoplePresenter {
        fun loadPopularPeople()
        fun cancel()
    }

}