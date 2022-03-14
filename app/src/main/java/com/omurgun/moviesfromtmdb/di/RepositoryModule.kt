package com.omurgun.moviesfromtmdb.di

import com.omurgun.moviesfromtmdb.data.local.room.dao.*
import com.omurgun.moviesfromtmdb.data.remote.TMDBService
import com.omurgun.moviesfromtmdb.data.repo.FavoriteMovieRepository
import com.omurgun.moviesfromtmdb.data.repo.MovieImageRepository
import com.omurgun.moviesfromtmdb.data.repo.MovieRepository
import com.omurgun.moviesfromtmdb.data.repo.SimilarMovieRepository
import com.omurgun.moviesfromtmdb.domain.repoInterfaces.IFavoriteMovieRepository
import com.omurgun.moviesfromtmdb.domain.repoInterfaces.IMovieImageRepository
import com.omurgun.moviesfromtmdb.domain.repoInterfaces.IMovieRepository
import com.omurgun.moviesfromtmdb.domain.repoInterfaces.ISimilarMovieRepository
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
    fun provideMovieRepo(api: TMDBService,movieDao : MovieDao) = MovieRepository(api,movieDao) as IMovieRepository

    @Singleton
    @Provides
    fun provideFavoriteMovieRepo(favoriteMovieDao: FavoriteMovieDao) = FavoriteMovieRepository(favoriteMovieDao) as IFavoriteMovieRepository

    @Singleton
    @Provides
    fun provideSimilarMovieRepo(api: TMDBService,similarMovieDao: SimilarMovieDao) = SimilarMovieRepository(api,similarMovieDao) as ISimilarMovieRepository

    @Singleton
    @Provides
    fun provideMovieImageRepo(api: TMDBService,movieBackdropDao: MovieBackdropDao,moviePosterDao: MoviePosterDao,movieLogoDao: MovieLogoDao) = MovieImageRepository(api,movieBackdropDao,movieLogoDao, moviePosterDao) as IMovieImageRepository
}