package com.omurgun.moviesfromtmdb.data.repo

import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants.GET_MOVIE
import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants.SEARCH_MOVIE
import com.omurgun.moviesfromtmdb.data.local.room.dao.MovieDao
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieDetail
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetPopularMovies
import com.omurgun.moviesfromtmdb.data.models.request.RequestSearchMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponsePopularMovie
import com.omurgun.moviesfromtmdb.data.remote.TMDBService
import com.omurgun.moviesfromtmdb.domain.repoInterfaces.IMovieRepository
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val tmdbService: TMDBService,
    private val movieDao : MovieDao

    ) : IMovieRepository {
    override fun getAllMoviesFromRoom(): List<ResponseMovie> {
        return movieDao.getAllMovies()
    }

    override fun getMovieFromRoom(movieId: Int): ResponseMovie? {
        return movieDao.getMovie(movieId)
    }

    override suspend fun insertAllMoviesToRoom(movies: List<ResponseMovie>) : List<Long> {
        return movieDao.insertAllMovies(*movies.toTypedArray())
    }

    override suspend fun insertMovieToRoom(movie: ResponseMovie) {
        movieDao.insertMovie(movie)
    }

    override suspend fun updateMovieFromRoom(movie: ResponseMovie) {
        movieDao.updateMovie(movie)
    }

    override suspend fun deleteMovieFromRoom(movie: ResponseMovie) {
        movieDao.deleteMovie(movie)
    }

    override suspend fun deleteAllMoviesFromRoom() {
        movieDao.deleteAllMovies()
    }

    override suspend fun getMovieDetailFromAPI(requestMovieDetail: RequestGetMovieDetail): ResponseMovie {
        return tmdbService.getMovieDetail("$GET_MOVIE/${requestMovieDetail.movieId}")
    }

    override suspend fun getPopularMoviesFromAPI(requestGetPopularMovies: RequestGetPopularMovies): ResponsePopularMovie {
        return tmdbService.getPopularMovies(currentPageCount = requestGetPopularMovies.currentPageCount)
    }

    override suspend fun searchMoviesFromAPI(requestSearchMovie: RequestSearchMovie): ResponsePopularMovie {
        return tmdbService.searchMovies(SEARCH_MOVIE, currentPageCount = requestSearchMovie.currentPageCount,query = requestSearchMovie.query)
    }


}