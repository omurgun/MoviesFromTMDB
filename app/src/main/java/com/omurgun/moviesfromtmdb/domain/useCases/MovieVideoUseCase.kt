package com.omurgun.moviesfromtmdb.domain.useCases

import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieVideos
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovieVideo
import com.omurgun.moviesfromtmdb.domain.repoInterfaces.IMovieVideoRepository
import com.omurgun.moviesfromtmdb.util.ResultData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class MovieVideoUseCase @Inject constructor(private val movieVideoRepository: IMovieVideoRepository) {

    fun getMovieVideosByMovieIdFromAPI(requestGetMovieVideos : RequestGetMovieVideos) : Flow<ResultData<ResponseMovieVideo>> = flow {
        try {
            emit(ResultData.Loading())
            val movieVideos = movieVideoRepository.getMovieVideosByMovieIdFromAPI(requestGetMovieVideos)
            emit(ResultData.Success(movieVideos))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }

}