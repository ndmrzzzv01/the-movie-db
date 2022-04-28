package com.example.themovies.screens.people

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PeoplePresenter(
    private val view: PeopleContract.PeopleView
) : PeopleContract.PeoplePresenter, CoroutineScope {

    private var peopleRepository = PeopleRepository()
    private var mainJob = SupervisorJob()

    override val coroutineContext: CoroutineContext = mainJob + Dispatchers.IO

    override fun loadPopularPeople() {
        launch {
            try {
                val listOfPeople = peopleRepository.getPopularPeople()
                launch(Dispatchers.Main) {
                    view.displayListOfPeople(listOfPeople)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                launch(Dispatchers.Main) {
                    view.onFail()
                }
            }
        }
    }

    override fun cancel() {
        mainJob.cancel()
    }

}