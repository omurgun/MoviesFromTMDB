package com.omurgun.moviesfromtmdb.data.local.room

import androidx.room.*
import com.omurgun.moviesfromtmdb.data.models.entity.EntitySimilarMovie

@Dao
interface SimilarMovieDao {

    @Query("SELECT * FROM similarMovies WHERE movieId = :movieId")
    fun getAllSimilarMovies(movieId : Int) : List<EntitySimilarMovie>

    @Query("SELECT * FROM similarMovies WHERE id = :id")
    fun getSimilarMovie(id : Int) : EntitySimilarMovie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSimilarMovies(vararg similarMovies: EntitySimilarMovie) : List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSimilarMovie(similarMovies : EntitySimilarMovie) : Long

    @Update
    suspend fun updateSimilarMovie(similarMovies: EntitySimilarMovie)

    @Query("DELETE FROM similarMovies WHERE movieId = :movieId")
    suspend fun deleteAllSimilarMovies(movieId : Int) : Int

    @Delete
    suspend fun deleteSimilarMovie(similarMovies: EntitySimilarMovie) : Int


}