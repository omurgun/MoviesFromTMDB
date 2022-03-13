package com.omurgun.moviesfromtmdb.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omurgun.moviesfromtmdb.data.models.internal.InternalFavoriteMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponseSimilarMovies

@Database(entities =
[
    InternalFavoriteMovie::class,
    ResponseMovie::class
],version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
    abstract fun similarMovieDao() : SimilarMovieDao
}