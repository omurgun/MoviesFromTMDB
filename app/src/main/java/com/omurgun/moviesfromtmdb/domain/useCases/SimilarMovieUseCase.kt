package com.omurgun.moviesfromtmdb.domain.useCases

import com.omurgun.moviesfromtmdb.data.models.request.RequestGetSimilarMovies
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponseSimilarMovie
import com.omurgun.moviesfromtmdb.domain.repoInterfaces.ISimilarMovieRepository
import com.omurgun.moviesfromtmdb.util.ResultData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class SimilarMovieUseCase @Inject constructor(private val similarMovieRepository: ISimilarMovieRepository) {

    fun getSimilarMoviesByMovieIdFromAPI(requestGetSimilarMovies : RequestGetSimilarMovies) : Flow<ResultData<ResponseSimilarMovie>> = flow {
        try {
            emit(ResultData.Loading())
            val similarMovies = similarMovieRepository.getSimilarMoviesByMovieIdFromAPI(requestGetSimilarMovies)
            emit(ResultData.Success(similarMovies))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }

    fun getAllSimilarMoviesFromRoom(movieId: Int) : Flow<ResultData<List<ResponseMovie>>> = flow {
        try {
            emit(ResultData.Loading())
            val similarMovies = similarMovieRepository.getAllSimilarMoviesFromRoom(movieId)
            emit(ResultData.Success(similarMovies))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }

    fun getSimilarMovieFromRoom(movieId: Int) : Flow<ResultData<ResponseMovie>> = flow {
        try {
            emit(ResultData.Loading())
            val similarMovies = similarMovieRepository.getSimilarMovieFromRoom(movieId)
            emit(ResultData.Success(similarMovies))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }

    fun insertAllSimilarMoviesToRoom(similarMovies: List<ResponseMovie>, movieId: Int) : Flow<ResultData<List<Long>>> = flow {
        try {
            emit(ResultData.Loading())
            val movies = similarMovieRepository.insertAllSimilarMoviesToRoom(similarMovies,movieId)
            emit(ResultData.Success(movies))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }

    fun deleteAllSimilarMoviesFromRoom(movieId: Int) : Flow<ResultData<Int>> = flow {
        try {
            emit(ResultData.Loading())
            val movies = similarMovieRepository.deleteAllSimilarMoviesFromRoom(movieId)
            emit(ResultData.Success(movies))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }
}
