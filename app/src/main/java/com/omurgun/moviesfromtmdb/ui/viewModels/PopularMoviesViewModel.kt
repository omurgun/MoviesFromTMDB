package com.omurgun.moviesfromtmdb.ui.viewModels


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetPopularMovies
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponsePopularMovie
import com.omurgun.moviesfromtmdb.domain.useCases.MovieUseCase
import com.omurgun.moviesfromtmdb.util.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers

import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun getPopularMoviesFromAPI(requestGetPopularMovies: RequestGetPopularMovies) : LiveData<ResultData<ResponsePopularMovie>> {
      return movieUseCase.getPopularMoviesFromAPI(requestGetPopularMovies).asLiveData(Dispatchers.IO)
    }

    fun savePopularMoviesFromRoom(movies : List<ResponseMovie>) : LiveData<ResultData<List<Long>>>  {
        return movieUseCase.insertAllMoviesToRoom(movies)
    }

    fun getAllMoviesFromRoom() : LiveData<ResultData<List<ResponseMovie>>>  {
        return movieUseCase.getAllMoviesFromRoom()
    }

}