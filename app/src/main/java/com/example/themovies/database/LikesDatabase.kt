package com.example.themovies.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.themovies.database.dao.LikesDao
import com.example.themovies.database.data.Like

@Database(entities = [Like::class], version = 1)
abstract class LikesDatabase: RoomDatabase() {
    abstract fun likesDao(): LikesDao
}