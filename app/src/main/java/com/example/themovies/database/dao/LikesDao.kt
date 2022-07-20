package com.example.themovies.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.themovies.database.data.Like

@Dao
interface LikesDao {

    @Query("SELECT * FROM likes")
    suspend fun getAllLikes(): List<Like>?

    @Query("SELECT * FROM likes WHERE id_record=(:id)")
    suspend fun getLike(id: Int): Like?

    @Insert
    suspend fun insertRecordToDatabase(like: Like)

    @Query("DELETE FROM likes WHERE id_record=(:id)")
    suspend fun deleteRecordFromDatabase(id: Int)
}