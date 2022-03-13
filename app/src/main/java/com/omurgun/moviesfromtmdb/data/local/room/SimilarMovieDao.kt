package com.omurgun.moviesfromtmdb.data.local.room

import androidx.room.*
import com.omurgun.moviesfromtmdb.data.models.internal.InternalFavoriteMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie

@Dao
interface SimilarMovieDao {

    @Query("SELECT * FROM favoriteMovies")
    fun getAllFavoriteMovies() : List<InternalFavoriteMovie>

    @Query("SELECT * FROM favoriteMovies WHERE id = :id")
    fun getFavoriteMovie(id : Int) : InternalFavoriteMovie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFavoriteMovies(vararg favoriteMovies: InternalFavoriteMovie) : List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(favoriteMovies : InternalFavoriteMovie) : Long

    @Update
    suspend fun updateFavoriteMovie(favoriteMovies: InternalFavoriteMovie)

    @Query("DELETE FROM favoriteMovies")
    suspend fun deleteAllFavoriteMovies()

    @Delete
    suspend fun deleteFavoriteMovie(favoriteMovies: InternalFavoriteMovie) : Int



}