package com.example.themovies.screens.movie.toprated

import com.example.themovies.api.MovieApi
import com.example.themovies.network.responses.MovieResponse
import com.example.themovies.utils.NetworkUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class TopRatedMoviesPresenter(
    private val view: TopRatedMoviesContract.TopRatedMoviesView
) : TopRatedMoviesContract.TopRatedMoviesPresenter {

    override fun loadTopRatedMovies() {
        getObservable()?.subscribeWith(getObserver())
    }

    private fun getObservable(): Observable<MovieResponse>? {
        return NetworkUtils.createRetrofit().create(MovieApi::class.java)
            .getTopRatedMovie()?.subscribeOn(Schedulers.io())
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