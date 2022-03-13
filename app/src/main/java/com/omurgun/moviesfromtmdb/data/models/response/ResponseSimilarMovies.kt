package com.omurgun.moviesfromtmdb.data.models.response

import com.google.gson.annotations.SerializedName

data class ResponseSimilarMovies(
    @SerializedName("page")
    val currentPage : Int,
    @SerializedName("total_pages")
    val maxPageCount : Int,
    @SerializedName("total_results")
    val maxMovieCount : Int,
    @SerializedName("results")
    var similarMovies : ArrayList<ResponseMovie>
) {

}