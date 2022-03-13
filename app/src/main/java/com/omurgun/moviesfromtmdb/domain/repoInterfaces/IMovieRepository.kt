package com.omurgun.moviesfromtmdb.domain.repoInterfaces

import androidx.lifecycle.LiveData
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovie
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetPopularMovies
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponsePopularMovies

interface IMovieRepository {
    fun getAllMoviesFromRoom() : List<ResponseMovie>
    fun getMovieFromRoom(movieId : Int) : ResponseMovie
    suspend fun insertAllMoviesToRoom(movies : List<ResponseMovie>) : List<Long>
    suspend fun insertMovieToRoom(movie : ResponseMovie)
    suspend fun updateMovieFromRoom(movie : ResponseMovie)
    suspend fun deleteMovieFromRoom(movie: ResponseMovie)
    suspend fun deleteAllMoviesFromRoom()
    suspend fun getMovieFromAPI(requestMovie: RequestGetMovie) : ResponseMovie
    suspend fun getPopularMoviesFromAPI(requestGetPopularMovies: RequestGetPopularMovies) : ResponsePopularMovies
}