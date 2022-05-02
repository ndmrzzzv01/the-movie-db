package com.example.themovies.screens.people

import androidx.paging.Pager
import com.example.themovies.data.ItemType

interface PeopleContract {

    interface PeopleView {
        fun displayListOfPeople(pager: Pager<Int, ItemType>)
        fun onFail()
    }

    interface PeoplePresenter {
        fun loadPopularPeople()
        fun cancel()
    }

}