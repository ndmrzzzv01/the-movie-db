package com.example.themovies.screens.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.themovies.data.paging.TvPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvViewModel @Inject constructor(
    private val tvRepository: TVRepository
) : ViewModel() {

    val flow = Pager(PagingConfig(20)) {
        TvPagingSource(tvRepository)
    }.flow.cachedIn(viewModelScope)

}