package com.omurgun.moviesfromtmdb.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie

@Database(entities =
[
    ResponseMovie::class,
],version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
}