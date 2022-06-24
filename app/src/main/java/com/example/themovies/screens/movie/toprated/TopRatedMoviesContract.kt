package com.example.themovies.screens.movie.toprated

import com.example.themovies.data.Movie

interface TopRatedMoviesContract {

    interface TopRatedMoviesView {
        fun displayListOfTopRatedMovies(list: List<Movie>)
        fun onFail()
    }

    interface TopRatedMoviesPresenter {
        fun loadTopRatedMovies()
        fun cancel()
    }
}