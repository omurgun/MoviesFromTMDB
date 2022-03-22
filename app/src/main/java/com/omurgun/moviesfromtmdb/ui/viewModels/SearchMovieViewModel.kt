package com.omurgun.moviesfromtmdb.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.omurgun.moviesfromtmdb.data.models.request.RequestSearchMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponsePopularMovie
import com.omurgun.moviesfromtmdb.domain.useCases.MovieUseCase
import com.omurgun.moviesfromtmdb.util.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@HiltViewModel
class SearchMovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun searchMoviesFromAPI(requestSearchMovie: RequestSearchMovie) : LiveData<ResultData<ResponsePopularMovie>> {
        return movieUseCase.searchMoviesFromAPI(requestSearchMovie).asLiveData(Dispatchers.IO)
    }

}