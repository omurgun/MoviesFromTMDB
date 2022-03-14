package com.omurgun.moviesfromtmdb.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omurgun.moviesfromtmdb.data.models.entity.EntityFavoriteMovie
import com.omurgun.moviesfromtmdb.data.models.entity.EntitySimilarMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie

@Database(entities =
[
    EntitySimilarMovie::class,
    EntityFavoriteMovie::class,
    ResponseMovie::class
],version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
    abstract fun favoriteMovieDao() : FavoriteMovieDao
    abstract fun SimilarMovieDao() : SimilarMovieDao
}