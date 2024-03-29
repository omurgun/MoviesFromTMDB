package com.omurgun.moviesfromtmdb.data.remote

import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants.CONSTANTS_API_KEY
import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants.CONSTANTS_MOVIE_SEARCH_QUERY
import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants.CONSTANTS_PAGE
import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants.GET_POPULAR_MOVIES
import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants.SECRET_API_KEY_VALUE
import com.omurgun.moviesfromtmdb.data.models.response.*
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
    suspend fun getMovieVideos(@Url url:String, @Query(CONSTANTS_API_KEY) apiKey: String = SECRET_API_KEY_VALUE) : ResponseMovieVideo

    @GET
    suspend fun getSimilarMovies(@Url url:String, @Query(CONSTANTS_API_KEY) apiKey: String = SECRET_API_KEY_VALUE, @Query(CONSTANTS_PAGE) currentPageCount: Int) : ResponseSimilarMovie

    @GET
    suspend fun searchMovies(@Url url:String, @Query(CONSTANTS_API_KEY) apiKey: String = SECRET_API_KEY_VALUE, @Query(CONSTANTS_PAGE) currentPageCount: Int,@Query(CONSTANTS_MOVIE_SEARCH_QUERY) query: String) : ResponsePopularMovie

}