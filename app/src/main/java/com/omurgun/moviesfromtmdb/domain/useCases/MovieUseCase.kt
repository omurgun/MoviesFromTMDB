package com.omurgun.moviesfromtmdb.domain.useCases

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieDetail
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieImages
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetPopularMovies
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetSimilarMovies
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovieImages
import com.omurgun.moviesfromtmdb.data.models.response.ResponsePopularMovies
import com.omurgun.moviesfromtmdb.data.models.response.ResponseSimilarMovies
import com.omurgun.moviesfromtmdb.domain.repoInterfaces.IMovieRepository
import com.omurgun.moviesfromtmdb.util.ResultData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class MovieUseCase @Inject constructor(private val movieRepository: IMovieRepository) {

    fun getMovieDetailFromAPI(requestMovieDetail: RequestGetMovieDetail) : Flow<ResultData<ResponseMovie>> = flow {
        try {
            emit(ResultData.Loading())
            val movie = movieRepository.getMovieDetailFromAPI(requestMovieDetail)
            emit(ResultData.Success(movie))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }

    fun getPopularMoviesFromAPI(requestGetPopularMovies: RequestGetPopularMovies) : Flow<ResultData<ResponsePopularMovies>> = flow {
        try {
            emit(ResultData.Loading())
            val movies = movieRepository.getPopularMoviesFromAPI(requestGetPopularMovies)
            emit(ResultData.Success(movies))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }

    fun getMovieImagesByMovieIdFromAPI(requestGetMovieImages : RequestGetMovieImages) : Flow<ResultData<ResponseMovieImages>> = flow {
        try {
            emit(ResultData.Loading())
            val movieImages = movieRepository.getMovieImagesByMovieIdFromAPI(requestGetMovieImages)
            emit(ResultData.Success(movieImages))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }


    fun getSimilarMoviesByMovieIdFromAPI(requestGetSimilarMovies : RequestGetSimilarMovies) : Flow<ResultData<ResponseSimilarMovies>> = flow {
        try {
            emit(ResultData.Loading())
            val similarMovies = movieRepository.getSimilarMoviesByMovieIdFromAPI(requestGetSimilarMovies)
            emit(ResultData.Success(similarMovies))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }


    fun getMovieFromRoom(requestMovieDetail: RequestGetMovieDetail) : LiveData<ResultData<ResponseMovie>> = flow {
        try {
            emit(ResultData.Loading())
            val movie = movieRepository.getMovieFromRoom(requestMovieDetail.movieId)
            emit(ResultData.Success(movie))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }.asLiveData(Dispatchers.IO)


    fun insertAllMoviesToRoom(movies : List<ResponseMovie>) : LiveData<ResultData<List<Long>>> = flow {
        try {
            emit(ResultData.Loading())
            val movie = movieRepository.insertAllMoviesToRoom(movies)
            emit(ResultData.Success(movie))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }.asLiveData(Dispatchers.IO)


    fun getAllMoviesFromRoom() : LiveData<ResultData<List<ResponseMovie>>> = flow {
        try {
            emit(ResultData.Loading())
            val movie = movieRepository.getAllMoviesFromRoom()
            emit(ResultData.Success(movie))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }.asLiveData(Dispatchers.IO)


























    fun getFavoriteMovieFromRoom(requestMovieDetail: RequestGetMovieDetail) : LiveData<ResultData<ResponseMovie>> = flow {
        try {
            emit(ResultData.Loading())
            val movie = movieRepository.getFavoriteMovieFromRoom(requestMovieDetail.movieId)
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
            val favoriteMovie = movieRepository.insertFavoriteMovieToRoom(movie)
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
            val favoriteMovies = movieRepository.getAllFavoriteMoviesFromRoom()
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
            val favoriteMovies = movieRepository.deleteFavoriteMovieFromRoom(movie)
            emit(ResultData.Success(favoriteMovies))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }.asLiveData(Dispatchers.IO)






}