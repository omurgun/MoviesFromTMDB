package com.omurgun.moviesfromtmdb.domain.repoInterfaces

import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieDetail
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieImages
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetPopularMovies
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetSimilarMovies
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovieImages
import com.omurgun.moviesfromtmdb.data.models.response.ResponsePopularMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponseSimilarMovie

interface IMovieRepository {
    fun getAllMoviesFromRoom() : List<ResponseMovie>
    fun getMovieFromRoom(movieId : Int) : ResponseMovie?
    suspend fun insertAllMoviesToRoom(movies : List<ResponseMovie>) : List<Long>
    suspend fun insertMovieToRoom(movie : ResponseMovie)
    suspend fun updateMovieFromRoom(movie : ResponseMovie)
    suspend fun deleteMovieFromRoom(movie: ResponseMovie)
    suspend fun deleteAllMoviesFromRoom()
    suspend fun getMovieDetailFromAPI(requestMovieDetail: RequestGetMovieDetail) : ResponseMovie
    suspend fun getPopularMoviesFromAPI(requestGetPopularMovies: RequestGetPopularMovies) : ResponsePopularMovie
    suspend fun getMovieImagesByMovieIdFromAPI(requestGetMovieImages : RequestGetMovieImages) : ResponseMovieImages
}