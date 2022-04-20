package com.example.themovies.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.themovies.data.Movie
import com.example.themovies.network.MoviesRepository

class MoviesPagingSource(
    private val moviesRepository: MoviesRepository
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val nextPageNumber = params.key ?: 1

        return try {
            val movies = moviesRepository.getMovies(nextPageNumber)
            val nextKey =
                if (movies.isEmpty()) null
                else nextPageNumber + 1

            LoadResult.Page(
                data = movies,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            Log.e("MoviesPagingSource", exception.message ?: "")
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            return anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}