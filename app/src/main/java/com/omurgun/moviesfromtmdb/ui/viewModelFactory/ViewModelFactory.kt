package com.omurgun.moviesfromtmdb.ui.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.omurgun.moviesfromtmdb.domain.useCases.FavoriteMovieUseCase
import com.omurgun.moviesfromtmdb.domain.useCases.MovieUseCase
import com.omurgun.moviesfromtmdb.domain.useCases.SimilarMovieUseCase
import com.omurgun.moviesfromtmdb.ui.viewModels.FavoriteMoviesViewModel
import com.omurgun.moviesfromtmdb.ui.viewModels.MovieDetailViewModel
import com.omurgun.moviesfromtmdb.ui.viewModels.PopularMoviesViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val similarMovieUseCase: SimilarMovieUseCase,
    private val favoriteMovieUseCase: FavoriteMovieUseCase

): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass.name) {
            PopularMoviesViewModel::class.java.name -> PopularMoviesViewModel(movieUseCase) as T
            MovieDetailViewModel::class.java.name -> MovieDetailViewModel(movieUseCase,similarMovieUseCase,favoriteMovieUseCase) as T
            FavoriteMoviesViewModel::class.java.name -> FavoriteMoviesViewModel(favoriteMovieUseCase) as T

            else -> throw IllegalArgumentException("ViewModel Not Found")
        }


    }
}