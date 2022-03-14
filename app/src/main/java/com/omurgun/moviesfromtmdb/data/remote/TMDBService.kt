package com.omurgun.moviesfromtmdb.data.remote

import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants.CONSTANTS_API_KEY
import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants.CONSTANTS_PAGE
import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants.GET_POPULAR_MOVIES
import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants.SECRET_API_KEY_VALUE
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovieImages
import com.omurgun.moviesfromtmdb.data.models.response.ResponsePopularMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponseSimilarMovie
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface TMDBService {
    @GET
    suspend fun getMovieDetail(@Url url:String, @Query(CONSTANTS_API_KEY) apiKey: String = SECRET_API_KEY_VALUE)  : ResponseMovie

    @GET(GET_POPULAR_MOVIES)
    suspend fun getPopularMovies(@Query(CONSTANTS_API_KEY) apiKey: String = SECRET_API_KEY_VALUE,@Query(CONSTANTS_PAGE) currentPageCount: Int) : ResponsePopularMovie

    @GET
    suspend fun getMovieImages(@Url url:String, @Query(CONSTANTS_API_KEY) apiKey: String = SECRET_API_KEY_VALUE) : ResponseMovieImages

    @GET
    suspend fun getSimilarMovies(@Url url:String, @Query(CONSTANTS_API_KEY) apiKey: String = SECRET_API_KEY_VALUE, @Query(CONSTANTS_PAGE) currentPageCount: Int) : ResponseSimilarMovie

}