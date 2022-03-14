package com.omurgun.moviesfromtmdb.data.local.room.dao

import androidx.room.*
import com.omurgun.moviesfromtmdb.data.models.entity.EntityMovieImagePoster

@Dao
interface MoviePosterDao {

    @Query("SELECT * FROM posters WHERE movieId = :movieId")
    fun getAllPosters(movieId : Int) : List<EntityMovieImagePoster>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPosters(vararg posters: EntityMovieImagePoster) : List<Long>

    @Query("DELETE FROM posters WHERE movieId = :movieId")
    suspend fun deleteAllPosters(movieId : Int) : Int



}