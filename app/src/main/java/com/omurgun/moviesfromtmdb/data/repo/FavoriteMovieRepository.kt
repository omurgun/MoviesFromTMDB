package com.omurgun.moviesfromtmdb.data.repo


import com.omurgun.moviesfromtmdb.data.local.room.FavoriteMovieDao
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.domain.repoInterfaces.IFavoriteMovieRepository

import javax.inject.Inject

class FavoriteMovieRepository @Inject constructor(
    private val favoriteMovieDao : FavoriteMovieDao

) : IFavoriteMovieRepository {

    override fun getAllFavoriteMoviesFromRoom(): List<ResponseMovie> {
        return favoriteMovieDao.getAllFavoriteMovies().map { it.toResponseMovie() }
    }

    override fun getFavoriteMovieFromRoom(movieId: Int): ResponseMovie? {
        return favoriteMovieDao.getFavoriteMovie(movieId)?.toResponseMovie()
    }

    override suspend fun insertFavoriteMovieToRoom(movie: ResponseMovie) : Long {
        return favoriteMovieDao.insertFavoriteMovie(movie.toEntityFavoriteMovie())
    }

    override suspend fun deleteFavoriteMovieFromRoom(movie: ResponseMovie): Int {
        return favoriteMovieDao.deleteFavoriteMovie(movie.toEntityFavoriteMovie())
    }


}