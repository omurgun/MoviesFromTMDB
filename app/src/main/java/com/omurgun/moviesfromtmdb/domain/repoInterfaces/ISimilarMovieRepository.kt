package com.omurgun.moviesfromtmdb.domain.repoInterfaces


import com.omurgun.moviesfromtmdb.data.models.request.RequestGetSimilarMovies
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponseSimilarMovie

interface ISimilarMovieRepository {
    suspend fun getSimilarMoviesByMovieIdFromAPI(requestGetSimilarMovies : RequestGetSimilarMovies) : ResponseSimilarMovie
    fun getAllSimilarMoviesFromRoom(movieId: Int) : List<ResponseMovie>
    fun getSimilarMovieFromRoom(movieId : Int) : ResponseMovie?
    suspend fun insertAllSimilarMoviesToRoom(similarMovies : List<ResponseMovie>,movieId: Int) : List<Long>
    suspend fun deleteAllSimilarMoviesFromRoom(movieId: Int) : Int

}