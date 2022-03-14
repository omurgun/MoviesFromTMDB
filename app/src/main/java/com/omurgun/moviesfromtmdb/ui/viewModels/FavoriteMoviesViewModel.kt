package com.omurgun.moviesfromtmdb.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.domain.useCases.FavoriteMovieUseCase
import com.omurgun.moviesfromtmdb.domain.useCases.MovieUseCase
import com.omurgun.moviesfromtmdb.util.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(private val favoriteMovieUseCase: FavoriteMovieUseCase) : ViewModel() {

    fun getAllFavoriteMoviesFromRoom() : LiveData<ResultData<List<ResponseMovie>>> {
        return favoriteMovieUseCase.getAllFavoriteMoviesFromRoom()
    }



}