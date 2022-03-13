package com.omurgun.moviesfromtmdb.di

import com.omurgun.moviesfromtmdb.domain.repoInterfaces.IMovieRepository
import com.omurgun.moviesfromtmdb.domain.useCases.MovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideMovieUseCase(movieRepository: IMovieRepository) = MovieUseCase(movieRepository)
}