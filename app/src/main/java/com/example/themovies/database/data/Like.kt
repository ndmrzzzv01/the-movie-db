package com.example.themovies.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "likes")
data class Like(
    @ColumnInfo(name = "id_record") val idRecord: Int?,
    @ColumnInfo(name = "type") val type: Int?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
