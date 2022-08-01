package com.example.themovies.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.themovies.network.data.MediaItemType
import com.example.themovies.network.data.RecordType
import timber.log.Timber

class TheMovieDBPagingSource (
    private val loadData: suspend (page: Int) -> List<MediaItemType>
) : PagingSource<Int, MediaItemType>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MediaItemType> {
        val nextPageNumber = params.key ?: 1

        return try {
            val list = loadData(nextPageNumber)
            val nextKey =
                if (list.isEmpty()) null
                else nextPageNumber + 1

            LoadResult.Page(
                data = list,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            Timber.e(exception.message)
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MediaItemType>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            return anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}