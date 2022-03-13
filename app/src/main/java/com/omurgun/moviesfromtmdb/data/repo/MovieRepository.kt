package com.omurgun.moviesfromtmdb.data.repo

import androidx.lifecycle.LiveData
import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants.GET_MOVIE
import com.omurgun.moviesfromtmdb.data.local.room.MovieDao
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovie
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetPopularMovies
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponsePopularMovies
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

    override fun getMovieFromRoom(movieId: Int): ResponseMovie {
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

    override suspend fun getMovieFromAPI(requestMovie: RequestGetMovie): ResponseMovie {
        return tmdbService.getMovie("$GET_MOVIE/${requestMovie.movieId}")
    }

    override suspend fun getPopularMoviesFromAPI(requestGetPopularMovies: RequestGetPopularMovies): ResponsePopularMovies {
        return tmdbService.getPopularMovies(currentPageCount = requestGetPopularMovies.currentPageCount)
    }

}