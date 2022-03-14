package com.omurgun.moviesfromtmdb.data.local.room.dao

import androidx.room.*
import com.omurgun.moviesfromtmdb.data.models.entity.EntityMovieImageLogo

@Dao
interface MovieLogoDao {

    @Query("SELECT * FROM logos WHERE movieId = :movieId")
    fun getAllLogos(movieId : Int) : List<EntityMovieImageLogo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLogos(vararg logos: EntityMovieImageLogo) : List<Long>

    @Query("DELETE FROM logos WHERE movieId = :movieId")
    suspend fun deleteAllLogos(movieId : Int) : Int



}