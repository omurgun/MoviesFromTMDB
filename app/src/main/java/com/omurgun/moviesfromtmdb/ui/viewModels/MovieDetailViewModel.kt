package com.omurgun.moviesfromtmdb.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieDetail
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieImages
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetSimilarMovies
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovieImages
import com.omurgun.moviesfromtmdb.data.models.response.ResponseSimilarMovies
import com.omurgun.moviesfromtmdb.domain.useCases.MovieUseCase
import com.omurgun.moviesfromtmdb.util.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun getMovieDetailFromAPI(requestMovieDetail: RequestGetMovieDetail) : LiveData<ResultData<ResponseMovie>> {
        return movieUseCase.getMovieDetailFromAPI(requestMovieDetail).asLiveData(Dispatchers.IO)
    }

    fun getMovieImagesByMovieIdFromAPI(requestGetMovieImages : RequestGetMovieImages) : LiveData<ResultData<ResponseMovieImages>> {
        return movieUseCase.getMovieImagesByMovieIdFromAPI(requestGetMovieImages).asLiveData(Dispatchers.IO)
    }

    fun getMovieImagesByMovieIdFromAPI(requestGetSimilarMovies : RequestGetSimilarMovies) : LiveData<ResultData<ResponseSimilarMovies>> {
        return movieUseCase.getSimilarMoviesByMovieIdFromAPI(requestGetSimilarMovies).asLiveData(Dispatchers.IO)
    }

}