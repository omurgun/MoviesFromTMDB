package com.omurgun.moviesfromtmdb.data.models.request

data class RequestGetSimilarMovies(
    val movieId : Int,
    val currentPageCount : Int
) {
}