package com.example.themovies.database.di

import android.content.Context
import androidx.room.Room
import com.example.themovies.database.LikesDatabase
import com.example.themovies.database.dao.LikesDao
import com.example.themovies.screens.likes.repositories.LikesRepositoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): LikesDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            LikesDatabase::class.java,
            LikesRepositoryDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun providesLikesDao(likesDatabase: LikesDatabase): LikesDao {
        return likesDatabase.likesDao()
    }
}