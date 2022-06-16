package com.example.themovies.screens.likes

import com.example.themovies.data.RecordType
import com.example.themovies.screens.likes.repositories.LikesRepositoryDatabase
import com.example.themovies.screens.likes.repositories.LikesTypeRepositoryNetwork
import javax.inject.Inject

class LikesCommonRepository @Inject constructor(
    private val likesDb: LikesRepositoryDatabase,
    private val likesNetwork: LikesTypeRepositoryNetwork
) {

    suspend fun getLikes(): MutableList<RecordType?> {
        val listLikes = likesDb.getAllLikes()
        val recordList = ArrayList<RecordType?>(listLikes?.size ?: 1)
        listLikes?.forEach {
            when (it.type) {
                0 -> recordList.add(likesNetwork.getMovie(it.idRecord))
                1 -> recordList.add(likesNetwork.getTv(it.idRecord ?: 0))
                2 -> recordList.add(likesNetwork.getPeople(it.idRecord))
                else -> null
            }
        }

        return recordList
    }


}