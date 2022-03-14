package com.omurgun.moviesfromtmdb.domain.useCases

import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieImages
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
}