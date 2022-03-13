package com.omurgun.moviesfromtmdb.data.models.internal


import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovieImage

sealed class InternalHorizontalMovieImageItem {

    class MovieImageSmall(
        val movieImage: ResponseMovieImage
    ) : InternalHorizontalMovieImageItem(),IGame

    class MovieImageSmallViewAll(
        val movieImage: ResponseMovieImage
    ) : InternalHorizontalMovieImageItem(),IGame

    class MovieImageMedium(
        val movieImage: ResponseMovieImage

    ) : InternalHorizontalMovieImageItem(),IGame

    class MovieImageLarge(
        val movieImage: ResponseMovieImage
    ) : InternalHorizontalMovieImageItem(),IGame

    interface IGame{

    }
}