package com.example.themovies.screens.likes.repositories

import com.example.themovies.database.Like
import com.example.themovies.database.LikesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class LikesRepositoryDatabase @Inject constructor(var likesDao: LikesDao) {

    companion object {
        const val DATABASE_NAME = "main-database"
    }

    suspend fun getAllLikes() = likesDao.getAllLikes()

    suspend fun isLiked(id: Int): Boolean {
        likesDao.getLike(id) ?: return false
        return true
    }

    suspend fun insertRecordToDatabase(like: Like) = likesDao.insertRecordToDatabase(like)

    suspend fun deleteRecordFromDatabase(id: Int) = likesDao.deleteRecordFromDatabase(id)

}