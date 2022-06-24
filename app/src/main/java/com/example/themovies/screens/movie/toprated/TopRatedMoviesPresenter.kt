package com.example.themovies.screens.movie.toprated

import android.util.Log
import com.example.themovies.api.MovieApi
import com.example.themovies.network.responses.MovieResponse
import com.example.themovies.utils.NetworkUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class TopRatedMoviesPresenter(
    private val view: TopRatedMoviesContract.TopRatedMoviesView
) : TopRatedMoviesContract.TopRatedMoviesPresenter {

    companion object {
        val TAG = TopRatedMoviesPresenter::class.simpleName
    }

    override fun loadTopRatedMovies() {
        getObservable()?.subscribeWith(getObserver())
    }

    private fun getObservable(): Observable<MovieResponse>? {
        return NetworkUtils.createRetrofit().create(MovieApi::class.java)
            .getTopRatedMovie()?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
    }

    private fun getObserver(): DisposableObserver<MovieResponse> {
        return object : DisposableObserver<MovieResponse>() {
            override fun onNext(movieResponse: MovieResponse) {
                Log.d(TAG, "OnNext ${movieResponse.results}")
                view.displayListOfTopRatedMovies(movieResponse.results ?: listOf())
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, e.message ?: "")
                view.onFail()
            }

            override fun onComplete() {
                Log.d(TAG, "OnComplete")
            }

        }
    }

    override fun cancel() {
        Log.d(TAG, "Cancel")
    }

}