package com.omurgun.moviesfromtmdb.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieDetail
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieImages
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetSimilarMovies
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovieImages
import com.omurgun.moviesfromtmdb.data.models.response.ResponseSimilarMovie
import com.omurgun.moviesfromtmdb.domain.useCases.FavoriteMovieUseCase
import com.omurgun.moviesfromtmdb.domain.useCases.MovieUseCase
import com.omurgun.moviesfromtmdb.domain.useCases.SimilarMovieUseCase
import com.omurgun.moviesfromtmdb.util.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase,private val similarMovieUseCase: SimilarMovieUseCase,private val favoriteMovieUseCase: FavoriteMovieUseCase) : ViewModel() {

    fun getMovieDetailFromAPI(requestMovieDetail: RequestGetMovieDetail) : LiveData<ResultData<ResponseMovie>> {
        return movieUseCase.getMovieDetailFromAPI(requestMovieDetail).asLiveData(Dispatchers.IO)
    }

    fun getMovieImagesByMovieIdFromAPI(requestGetMovieImages : RequestGetMovieImages) : LiveData<ResultData<ResponseMovieImages>> {
        return movieUseCase.getMovieImagesByMovieIdFromAPI(requestGetMovieImages).asLiveData(Dispatchers.IO)
    }

    fun getSimilarMoviesByMovieIdFromAPI(requestGetSimilarMovies : RequestGetSimilarMovies) : LiveData<ResultData<ResponseSimilarMovie>> {
        return similarMovieUseCase.getSimilarMoviesByMovieIdFromAPI(requestGetSimilarMovies).asLiveData(Dispatchers.IO)
    }

    fun saveFavoriteMovieToRoom(movie : ResponseMovie) : LiveData<ResultData<Long>> {
        return favoriteMovieUseCase.insertFavoriteMovieToRoom(movie)
    }

    fun getMovieFromRoom(requestMovieDetail: RequestGetMovieDetail) : LiveData<ResultData<ResponseMovie?>> {
        return movieUseCase.getMovieFromRoom(requestMovieDetail)
    }

    fun getFavoriteMovieFromRoom(requestMovieDetail: RequestGetMovieDetail) : LiveData<ResultData<ResponseMovie>> {
        return favoriteMovieUseCase.getFavoriteMovieFromRoom(requestMovieDetail)
    }

    fun deleteFavoriteMovieFromRoom(movie : ResponseMovie) : LiveData<ResultData<Int>> {
        return favoriteMovieUseCase.deleteFavoriteMovieFromRoom(movie)
    }

    fun getAllSimilarMoviesFromRoom(movieId: Int) : LiveData<ResultData<List<ResponseMovie>>> {
        return similarMovieUseCase.getAllSimilarMoviesFromRoom(movieId).asLiveData(Dispatchers.IO)
    }

    fun getSimilarMovieFromRoom(movieId: Int) : LiveData<ResultData<ResponseMovie>> {
        return similarMovieUseCase.getSimilarMovieFromRoom(movieId).asLiveData(Dispatchers.IO)
    }

    fun insertAllSimilarMoviesToRoom(similarMovies: List<ResponseMovie>, movieId: Int) : LiveData<ResultData<List<Long>>> {
        return similarMovieUseCase.insertAllSimilarMoviesToRoom(similarMovies,movieId).asLiveData(Dispatchers.IO)
    }

    fun deleteAllSimilarMoviesFromRoom(movieId: Int) : LiveData<ResultData<Int>> {
        return similarMovieUseCase.deleteAllSimilarMoviesFromRoom(movieId).asLiveData(Dispatchers.IO)
    }


}

