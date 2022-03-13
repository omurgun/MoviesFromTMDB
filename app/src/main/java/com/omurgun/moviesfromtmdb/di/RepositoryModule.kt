package com.omurgun.moviesfromtmdb.di

import com.omurgun.moviesfromtmdb.data.local.room.MovieDao
import com.omurgun.moviesfromtmdb.data.local.room.SimilarMovieDao
import com.omurgun.moviesfromtmdb.data.remote.TMDBService
import com.omurgun.moviesfromtmdb.data.repo.MovieRepository
import com.omurgun.moviesfromtmdb.domain.repoInterfaces.IMovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepo(api: TMDBService,movieDao : MovieDao,similarMovieDao: SimilarMovieDao) = MovieRepository(api,movieDao,similarMovieDao) as IMovieRepository
}