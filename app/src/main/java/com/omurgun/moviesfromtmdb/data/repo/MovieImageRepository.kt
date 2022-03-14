package com.omurgun.moviesfromtmdb.data.repo

import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants
import com.omurgun.moviesfromtmdb.data.local.room.dao.MovieBackdropDao
import com.omurgun.moviesfromtmdb.data.local.room.dao.MovieLogoDao
import com.omurgun.moviesfromtmdb.data.local.room.dao.MoviePosterDao
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieImages
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovieImage
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovieImages
import com.omurgun.moviesfromtmdb.data.remote.TMDBService
import com.omurgun.moviesfromtmdb.domain.repoInterfaces.IMovieImageRepository
import javax.inject.Inject

class MovieImageRepository  @Inject constructor(
    private val tmdbService: TMDBService,
    private val movieBackdropDao: MovieBackdropDao,
    private val movieLogoDao: MovieLogoDao,
    private val moviePosterDao: MoviePosterDao,

) : IMovieImageRepository {

    override suspend fun getMovieImagesByMovieIdFromAPI(requestGetMovieImages: RequestGetMovieImages): ResponseMovieImages {
        return tmdbService.getMovieImages("${NetworkConstants.GET_MOVIE}/${requestGetMovieImages.movieId}/${NetworkConstants.CONSTANTS_MOVIE_IMAGES}")
    }

    override fun getAllBackdropsFromRoom(movieId: Int): List<ResponseMovieImage> {
       return movieBackdropDao.getAllBackdrops(movieId).map { it.toResponseMovieImage() }
    }

    override suspend fun insertAllBackdropsToRoom(
        images: List<ResponseMovieImage>,
        movieId: Int
    ): List<Long> {
        return movieBackdropDao.insertAllBackdrops(*images.map { it.toEntityMovieImageBackdrop(1,movieId) }.toTypedArray())
    }

    override suspend fun deleteAllBackdropsFromRoom(movieId: Int): Int {
        return movieBackdropDao.deleteAllBackdrops(movieId)
    }

    override fun getAllLogosFromRoom(movieId: Int): List<ResponseMovieImage> {
        return movieLogoDao.getAllLogos(movieId).map { it.toResponseMovieImage() }
    }

    override suspend fun insertAllLogosToRoom(
        images: List<ResponseMovieImage>,
        movieId: Int
    ): List<Long> {
        return movieLogoDao.insertAllLogos(*images.map { it.toEntityMovieImageLogo(1,movieId) }.toTypedArray())
    }

    override suspend fun deleteAllLogosFromRoom(movieId: Int): Int {
        return movieLogoDao.deleteAllLogos(movieId)
    }

    override fun getAllPostersFromRoom(movieId: Int): List<ResponseMovieImage> {
        return moviePosterDao.getAllPosters(movieId).map { it.toResponseMovieImage() }
    }

    override suspend fun insertAllPostersToRoom(
        images: List<ResponseMovieImage>,
        movieId: Int
    ): List<Long> {
        return moviePosterDao.insertAllPosters(*images.map { it.toEntityMovieImagePoster(1,movieId) }.toTypedArray())
    }

    override suspend fun deleteAllPostersFromRoom(movieId: Int): Int {
        return moviePosterDao.deleteAllPosters(movieId)
    }
}