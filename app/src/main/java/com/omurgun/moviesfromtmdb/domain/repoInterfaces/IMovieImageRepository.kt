package com.omurgun.moviesfromtmdb.domain.repoInterfaces


import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieImages
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovieImage
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovieImages

interface IMovieImageRepository {
    suspend fun getMovieImagesByMovieIdFromAPI(requestGetMovieImages : RequestGetMovieImages) : ResponseMovieImages
    fun getAllBackdropsFromRoom(movieId: Int) : List<ResponseMovieImage>
    suspend fun insertAllBackdropsToRoom(images : List<ResponseMovieImage>, movieId: Int) : List<Long>
    suspend fun deleteAllBackdropsFromRoom(movieId: Int) : Int
    fun getAllLogosFromRoom(movieId: Int) : List<ResponseMovieImage>
    suspend fun insertAllLogosToRoom(images : List<ResponseMovieImage>, movieId: Int) : List<Long>
    suspend fun deleteAllLogosFromRoom(movieId: Int) : Int
    fun getAllPostersFromRoom(movieId: Int) : List<ResponseMovieImage>
    suspend fun insertAllPostersToRoom(images : List<ResponseMovieImage>, movieId: Int) : List<Long>
    suspend fun deleteAllPostersFromRoom(movieId: Int) : Int

}