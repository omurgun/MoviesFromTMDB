package com.omurgun.moviesfromtmdb.domain.repoInterfaces

import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieVideos
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovieVideo

interface IMovieVideoRepository {
    suspend fun getMovieVideosByMovieIdFromAPI(requestGetMovieVideos : RequestGetMovieVideos) : ResponseMovieVideo
}