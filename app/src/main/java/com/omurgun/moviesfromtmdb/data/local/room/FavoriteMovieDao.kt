package com.omurgun.moviesfromtmdb.data.local.room

import androidx.room.*
import com.omurgun.moviesfromtmdb.data.models.entity.EntityFavoriteMovie

@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM favoriteMovies")
    fun getAllFavoriteMovies() : List<EntityFavoriteMovie>

    @Query("SELECT * FROM favoriteMovies WHERE id = :id")
    fun getFavoriteMovie(id : Int) : EntityFavoriteMovie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFavoriteMovies(vararg favoriteMovies: EntityFavoriteMovie) : List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(favoriteMovies : EntityFavoriteMovie) : Long

    @Update
    suspend fun updateFavoriteMovie(favoriteMovies: EntityFavoriteMovie)

    @Query("DELETE FROM favoriteMovies")
    suspend fun deleteAllFavoriteMovies()

    @Delete
    suspend fun deleteFavoriteMovie(favoriteMovies: EntityFavoriteMovie) : Int



}