package com.example.themovies.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Like::class], version = 1)
abstract class LikesDatabase: RoomDatabase() {
    abstract fun likesDao(): LikesDao
}