package com.example.themovies.screens.people

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.themovies.data.paging.TheMovieDBPagingSource
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PeoplePresenter(
    private val view: PeopleContract.PeopleView,
) : PeopleContract.PeoplePresenter, CoroutineScope {

    private var mainJob = SupervisorJob()
    private var peopleRepository = PeopleRepository()

    override val coroutineContext: CoroutineContext = mainJob + Dispatchers.IO

    override fun loadPopularPeople() {
        try {
            view.displayListOfPeople(Pager(PagingConfig(20)) {
                TheMovieDBPagingSource { page ->
                    withContext(Dispatchers.IO) {
                        if (page >= 2) {
                            delay(2000L)
                        }
                        peopleRepository.getPopularPeople(page)
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            view.onFail()
        }
    }


    override fun cancel() {
        mainJob.cancel()
    }

}