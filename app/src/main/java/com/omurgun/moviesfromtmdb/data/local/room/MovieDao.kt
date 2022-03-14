package com.omurgun.moviesfromtmdb.data.local.room

import androidx.room.*
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAllMovies() : List<ResponseMovie>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovie(id : Int) : ResponseMovie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(vararg movies: ResponseMovie) : List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie : ResponseMovie)

    @Update
    suspend fun updateMovie(movie: ResponseMovie)

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()

    @Delete
    suspend fun deleteMovie(movie: ResponseMovie)



}