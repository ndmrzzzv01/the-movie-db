package com.example.themovies.screens.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.themovies.data.paging.TheMovieDBPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val peopleRepository: PeopleRepository
) : ViewModel() {

    val flow = Pager(PagingConfig(20)) {
        TheMovieDBPagingSource{ page ->
            withContext(Dispatchers.IO) {
                peopleRepository.getPopularPeople(page)
            }
        }
    }.flow.cachedIn(viewModelScope)

}