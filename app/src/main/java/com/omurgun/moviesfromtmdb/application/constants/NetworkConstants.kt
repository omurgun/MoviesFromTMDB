package com.omurgun.moviesfromtmdb.application.constants

object  NetworkConstants {

    const val BASE_URL = "https://api.themoviedb.org"
    const val IMAGES_BASE_URL = "https://image.tmdb.org/t/p/original"

    const val SECRET_API_KEY_VALUE = "0206ade6d661c1ccd273ada3ea52b93b"
    const val GET_MOVIE = "/3/movie"
    const val GET_POPULAR_MOVIES = "/3/movie/popular"


    //https://api.themoviedb.org/3/movie/551/similar?api_key=0206ade6d661c1ccd273ada3ea52b93b&language=en-US&page=1 // SIMILAR_MOVIE
    //https://api.themoviedb.org/3/movie/popular?api_key=0206ade6d661c1ccd273ada3ea52b93b&language=en-US&page=1
    //https://api.themoviedb.org/3/movie/551?api_key=0206ade6d661c1ccd273ada3ea52b93b&language=en-US // GET_MOVIE

    // https://api.themoviedb.org/3/movie/{movie_id}/similar?api_key=<<api_key>>&language=en-US&page=1




    const val CONSTANTS_API_KEY = "api_key"
    const val CONSTANTS_PAGE = "page"
    const val CONSTANTS_GET_DATA_FROM_LOCALE_TIME = "locale_get_data_from_locale"
    const val CONSTANTS_CURRENT_MOVIE_ID = "movie_id"
    const val CONSTANTS_GET_MOVIE_IMAGES_FROM_LOCALE_TIME = "locale_get_movie_images_from_locale"
    const val CONSTANTS_MOVIE_IMAGES = "images"
    const val CONSTANTS_MOVIE_VIDEOS = "videos"
    const val CONSTANTS_SIMILAR_MOVIE = "similar"

    const val ERROR_CONNECTION = "Check your internet connection."
    const val ERROR_HTTP = "You have received a system error."
}