package com.omurgun.moviesfromtmdb.data.repo

import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants.CONSTANTS_MOVIE_IMAGES
import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants.CONSTANTS_SIMILAR_MOVIE
import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants.GET_MOVIE
import com.omurgun.moviesfromtmdb.data.local.room.MovieDao
import com.omurgun.moviesfromtmdb.data.local.room.SimilarMovieDao
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieDetail
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieImages
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetPopularMovies
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetSimilarMovies
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovieImages
import com.omurgun.moviesfromtmdb.data.models.response.ResponsePopularMovies
import com.omurgun.moviesfromtmdb.data.models.response.ResponseSimilarMovies
import com.omurgun.moviesfromtmdb.data.remote.TMDBService
import com.omurgun.moviesfromtmdb.domain.repoInterfaces.IMovieRepository
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val tmdbService: TMDBService,
    private val movieDao : MovieDao,
    private val similarMovieDao : SimilarMovieDao

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

    override fun getAllFavoriteMoviesFromRoom(): List<ResponseMovie> {
        return similarMovieDao.getAllFavoriteMovies().map { it.toResponseMovie() }
    }

    override suspend fun insertFavoriteMovieToRoom(movie: ResponseMovie) : Long {
       return similarMovieDao.insertFavoriteMovie(movie.toInternalFavoriteMovie())
    }

    override suspend fun getMovieDetailFromAPI(requestMovieDetail: RequestGetMovieDetail): ResponseMovie {
        return tmdbService.getMovieDetail("$GET_MOVIE/${requestMovieDetail.movieId}")
    }

    override suspend fun getPopularMoviesFromAPI(requestGetPopularMovies: RequestGetPopularMovies): ResponsePopularMovies {
        return tmdbService.getPopularMovies(currentPageCount = requestGetPopularMovies.currentPageCount)
    }

    override suspend fun getMovieImagesByMovieIdFromAPI(requestGetMovieImages: RequestGetMovieImages): ResponseMovieImages {
        return tmdbService.getMovieImages("$GET_MOVIE/${requestGetMovieImages.movieId}/${CONSTANTS_MOVIE_IMAGES}")
    }

    override suspend fun getSimilarMoviesByMovieIdFromAPI(requestGetSimilarMovies: RequestGetSimilarMovies): ResponseSimilarMovies {
        return tmdbService.getSimilarMovies("$GET_MOVIE/${requestGetSimilarMovies.movieId}/$CONSTANTS_SIMILAR_MOVIE", currentPageCount = requestGetSimilarMovies.currentPageCount)
    }

}