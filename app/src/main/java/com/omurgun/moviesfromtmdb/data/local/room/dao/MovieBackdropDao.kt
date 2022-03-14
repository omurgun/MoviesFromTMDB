package com.omurgun.moviesfromtmdb.data.local.room.dao

import androidx.room.*
import com.omurgun.moviesfromtmdb.data.models.entity.EntityMovieImageBackdrop

@Dao
interface MovieBackdropDao {

    @Query("SELECT * FROM backdrops WHERE movieId = :movieId")
    fun getAllBackdrops(movieId : Int) : List<EntityMovieImageBackdrop>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBackdrops(vararg backdrops: EntityMovieImageBackdrop) : List<Long>

    @Query("DELETE FROM backdrops WHERE movieId = :movieId")
    suspend fun deleteAllBackdrops(movieId : Int) : Int



}