package com.example.themovies.screens.movie.toprated

import com.example.themovies.api.MovieApi
import com.example.themovies.network.responses.MovieResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class TopRatedMoviesPresenter @Inject constructor(
    val movieApi: MovieApi
) : TopRatedMoviesContract.TopRatedMoviesPresenter {

    private lateinit var view: TopRatedMoviesContract.TopRatedMoviesView

    fun setView(view: TopRatedMoviesContract.TopRatedMoviesView) {
        this.view = view
    }

    override fun loadTopRatedMovies() {
        getObservable()?.subscribeWith(getObserver())
    }

    private fun getObservable(): Observable<MovieResponse>? {
        return movieApi.getTopRatedMovie()?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
    }

    private fun getObserver(): DisposableObserver<MovieResponse> {
        return object : DisposableObserver<MovieResponse>() {
            override fun onNext(movieResponse: MovieResponse) {
                Timber.d("OnNext ${movieResponse.results}")
                view.displayListOfTopRatedMovies(movieResponse.results ?: listOf())
            }

            override fun onError(e: Throwable) {
                Timber.d(e.message ?: "")
                view.onFail()
            }

            override fun onComplete() {
                Timber.d("OnComplete")
            }

        }
    }

    override fun cancel() {
        Timber.d("Cancel")
    }

}