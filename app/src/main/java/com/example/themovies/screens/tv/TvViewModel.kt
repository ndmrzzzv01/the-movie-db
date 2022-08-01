package com.example.themovies.screens.tv

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.themovies.network.data.MediaItemType
import com.example.themovies.paging.TheMovieDBPagingSource
import com.example.themovies.screens.BaseListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TvViewModel @Inject constructor(
    private val tvRepository: TVRepository
) : BaseListViewModel() {

    val flow = Pager(PagingConfig(20)) {
        TheMovieDBPagingSource { page ->
            withContext(Dispatchers.IO) {
                val networkList = tvRepository.getPopularTV(page)
                val list = ArrayList<MediaItemType>(networkList.size)
                networkList.forEach {
                    list.add(MediaItemType(it.id, it.posterPath, it.name ?: ""))
                }
                return@withContext list
            }
        }
    }.flow.cachedIn(viewModelScope)

}