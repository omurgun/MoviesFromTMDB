package com.omurgun.moviesfromtmdb.data.repo

import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants
import com.omurgun.moviesfromtmdb.data.local.room.dao.SimilarMovieDao
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetSimilarMovies
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponseSimilarMovie
import com.omurgun.moviesfromtmdb.data.remote.TMDBService
import com.omurgun.moviesfromtmdb.domain.repoInterfaces.ISimilarMovieRepository
import javax.inject.Inject

class SimilarMovieRepository @Inject constructor(
    private val tmdbService: TMDBService,
    private val similarMovieDao: SimilarMovieDao

) : ISimilarMovieRepository {

    override suspend fun getSimilarMoviesByMovieIdFromAPI(requestGetSimilarMovies: RequestGetSimilarMovies): ResponseSimilarMovie {
        return tmdbService.getSimilarMovies("${NetworkConstants.GET_MOVIE}/${requestGetSimilarMovies.movieId}/${NetworkConstants.CONSTANTS_SIMILAR_MOVIE}", currentPageCount = requestGetSimilarMovies.currentPageCount)
    }

    override fun getAllSimilarMoviesFromRoom(movieId: Int): List<ResponseMovie> {
        return similarMovieDao.getAllSimilarMovies(movieId).map { it.toResponseMovie() }
    }

    override fun getSimilarMovieFromRoom(movieId: Int): ResponseMovie? {
        return similarMovieDao.getSimilarMovie(movieId)?.toResponseMovie()
    }

    override suspend fun insertAllSimilarMoviesToRoom(similarMovies: List<ResponseMovie>,movieId: Int): List<Long> {
       return similarMovieDao.insertAllSimilarMovies(*similarMovies.map { it.toEntitySimilarMovie(movieId) }.toTypedArray())
    }

    override suspend fun deleteAllSimilarMoviesFromRoom(movieId: Int): Int {
        return similarMovieDao.deleteAllSimilarMovies(movieId)
    }
}