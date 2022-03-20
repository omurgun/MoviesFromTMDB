package com.omurgun.moviesfromtmdb.data.repo


import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieVideos
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovieVideo
import com.omurgun.moviesfromtmdb.data.remote.TMDBService
import com.omurgun.moviesfromtmdb.domain.repoInterfaces.IMovieVideoRepository
import javax.inject.Inject

class MovieVideoRepository @Inject constructor(
    private val tmdbService: TMDBService
    ) : IMovieVideoRepository {
    override suspend fun getMovieVideosByMovieIdFromAPI(requestGetMovieVideos: RequestGetMovieVideos): ResponseMovieVideo {
       return tmdbService.getMovieVideos("${NetworkConstants.GET_MOVIE}/${requestGetMovieVideos.movieId}/${NetworkConstants.CONSTANTS_MOVIE_VIDEOS}")
    }

}