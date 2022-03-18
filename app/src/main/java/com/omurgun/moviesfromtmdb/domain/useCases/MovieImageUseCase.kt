package com.omurgun.moviesfromtmdb.domain.useCases

import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieImages
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovieImage
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovieImages
import com.omurgun.moviesfromtmdb.domain.repoInterfaces.IMovieImageRepository
import com.omurgun.moviesfromtmdb.util.ResultData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class MovieImageUseCase @Inject constructor(private val movieImageRepository: IMovieImageRepository) {

    fun getMovieImagesByMovieIdFromAPI(requestGetMovieImages : RequestGetMovieImages) : Flow<ResultData<ResponseMovieImages>> = flow {
        try {
            emit(ResultData.Loading())
            val movieImages = movieImageRepository.getMovieImagesByMovieIdFromAPI(requestGetMovieImages)
            emit(ResultData.Success(movieImages))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }

    fun getAllBackdropsFromRoom(movieId: Int) : Flow<ResultData<List<ResponseMovieImage>>> = flow {
        try {
            emit(ResultData.Loading())
            val movieImages = movieImageRepository.getAllBackdropsFromRoom(movieId)
            emit(ResultData.Success(movieImages))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }

    fun insertAllBackdropsToRoom(images: List<ResponseMovieImage>, movieId: Int) : Flow<ResultData<List<Long>>> = flow {
        try {
            emit(ResultData.Loading())
            val movieImages = movieImageRepository.insertAllBackdropsToRoom(images,movieId)
            emit(ResultData.Success(movieImages))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }

    fun deleteAllBackdropsFromRoom(movieId: Int) : Flow<ResultData<Int>> = flow {
        try {
            emit(ResultData.Loading())
            val movieImages = movieImageRepository.deleteAllBackdropsFromRoom(movieId)
            emit(ResultData.Success(movieImages))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }

    fun getAllLogosFromRoom(movieId: Int) : Flow<ResultData<List<ResponseMovieImage>>> = flow {
        try {
            emit(ResultData.Loading())
            val movieImages = movieImageRepository.getAllLogosFromRoom(movieId)
            emit(ResultData.Success(movieImages))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }

    fun insertAllLogosToRoom(images: List<ResponseMovieImage>, movieId: Int) : Flow<ResultData<List<Long>>> = flow {
        try {
            emit(ResultData.Loading())
            val movieImages = movieImageRepository.insertAllLogosToRoom(images,movieId)
            emit(ResultData.Success(movieImages))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }

    fun deleteAllLogosFromRoom(movieId: Int) : Flow<ResultData<Int>> = flow {
        try {
            emit(ResultData.Loading())
            val movieImages = movieImageRepository.deleteAllLogosFromRoom(movieId)
            emit(ResultData.Success(movieImages))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }

    fun getAllPostersFromRoom(movieId: Int) : Flow<ResultData<List<ResponseMovieImage>>> = flow {
        try {
            emit(ResultData.Loading())
            val movieImages = movieImageRepository.getAllPostersFromRoom(movieId)
            emit(ResultData.Success(movieImages))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }

    fun insertAllPostersToRoom(images: List<ResponseMovieImage>, movieId: Int) : Flow<ResultData<List<Long>>> = flow {
        try {
            emit(ResultData.Loading())
            val movieImages = movieImageRepository.insertAllPostersToRoom(images,movieId)
            emit(ResultData.Success(movieImages))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }

    fun deleteAllPostersFromRoom(movieId: Int) : Flow<ResultData<Int>> = flow {
        try {
            emit(ResultData.Loading())
            val movieImages = movieImageRepository.deleteAllPostersFromRoom(movieId)
            emit(ResultData.Success(movieImages))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }


}