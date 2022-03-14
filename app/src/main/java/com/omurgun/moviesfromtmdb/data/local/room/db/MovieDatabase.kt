package com.omurgun.moviesfromtmdb.data.local.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omurgun.moviesfromtmdb.data.local.room.dao.*
import com.omurgun.moviesfromtmdb.data.models.entity.*
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie

@Database(entities =
[
    EntityMovieImageBackdrop::class,
    EntityMovieImageLogo::class,
    EntityMovieImagePoster::class,
    EntitySimilarMovie::class,
    EntityFavoriteMovie::class,
    ResponseMovie::class
],version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
    abstract fun favoriteMovieDao() : FavoriteMovieDao
    abstract fun SimilarMovieDao() : SimilarMovieDao
    abstract fun MovieLogoDao() : MovieLogoDao
    abstract fun MovieBackdropDao() : MovieBackdropDao
    abstract fun MoviePosterDao() : MoviePosterDao
}