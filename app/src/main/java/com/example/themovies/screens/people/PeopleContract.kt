package com.example.themovies.screens.people

import androidx.paging.Pager
import com.example.themovies.data.RecordType

interface PeopleContract {

    interface PeopleView {
        fun displayListOfPeople(pager: Pager<Int, RecordType>)
        fun onFail()
    }

    interface PeoplePresenter {
        fun loadPopularPeople()
        fun cancel()
    }

}