package com.omurgun.moviesfromtmdb.di

import android.content.Context
import androidx.room.Room
import com.omurgun.moviesfromtmdb.data.local.room.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun injectMovieRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, MovieDatabase::class.java,"MovieDB").build()

    @Singleton
    @Provides
    fun injectMovieDao(
        database: MovieDatabase
    ) = database.movieDao()

    @Singleton
    @Provides
    fun injectSimilarMovieDao(
        database: MovieDatabase
    ) = database.similarMovieDao()
}