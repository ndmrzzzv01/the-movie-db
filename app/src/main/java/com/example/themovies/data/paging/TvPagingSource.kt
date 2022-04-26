package com.example.themovies.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.themovies.data.TV
import com.example.themovies.screens.tv.TVRepository

class TvPagingSource(
    private val tvRepository: TVRepository
) : PagingSource<Int, TV>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TV> {
        val nextPageNumber = params.key ?: 1

        return try {
            val tvShows = tvRepository.getPopularTV(nextPageNumber)
            val nextKey =
                if (tvShows.isEmpty()) null
                else nextPageNumber + 1

            LoadResult.Page(
                data = tvShows,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            Log.e("MoviesPagingSource", exception.message ?: "")
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TV>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            return anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}