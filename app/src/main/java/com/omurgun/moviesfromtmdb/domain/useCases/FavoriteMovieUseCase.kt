package com.omurgun.moviesfromtmdb.domain.useCases

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieDetail
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.domain.repoInterfaces.IFavoriteMovieRepository
import com.omurgun.moviesfromtmdb.util.ResultData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class FavoriteMovieUseCase @Inject constructor(private val favoriteMovieRepository: IFavoriteMovieRepository) {
    fun getFavoriteMovieFromRoom(requestMovieDetail: RequestGetMovieDetail) : LiveData<ResultData<ResponseMovie>> = flow {
        try {
            emit(ResultData.Loading())
            val movie = favoriteMovieRepository.getFavoriteMovieFromRoom(requestMovieDetail.movieId)
            emit(ResultData.Success(movie))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }.asLiveData(Dispatchers.IO)


    fun insertFavoriteMovieToRoom(movie : ResponseMovie) : LiveData<ResultData<Long>> = flow {
        try {
            emit(ResultData.Loading())
            val favoriteMovie = favoriteMovieRepository.insertFavoriteMovieToRoom(movie)
            emit(ResultData.Success(favoriteMovie))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }.asLiveData(Dispatchers.IO)


    fun getAllFavoriteMoviesFromRoom() : LiveData<ResultData<List<ResponseMovie>>> = flow {
        try {
            emit(ResultData.Loading())
            val favoriteMovies = favoriteMovieRepository.getAllFavoriteMoviesFromRoom()
            emit(ResultData.Success(favoriteMovies))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }.asLiveData(Dispatchers.IO)


    fun deleteFavoriteMovieFromRoom(movie : ResponseMovie) : LiveData<ResultData<Int>> = flow {
        try {
            emit(ResultData.Loading())
            val favoriteMovies = favoriteMovieRepository.deleteFavoriteMovieFromRoom(movie)
            emit(ResultData.Success(favoriteMovies))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }.asLiveData(Dispatchers.IO)
}