package com.omurgun.moviesfromtmdb.domain.repoInterfaces

import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie

interface IFavoriteMovieRepository {
    fun getAllFavoriteMoviesFromRoom() : List<ResponseMovie>
    fun getFavoriteMovieFromRoom(movieId : Int) : ResponseMovie?
    suspend fun insertFavoriteMovieToRoom(movie : ResponseMovie) : Long
    suspend fun deleteFavoriteMovieFromRoom(movie : ResponseMovie) : Int
}