package com.omurgun.moviesfromtmdb.data.models.response

import com.google.gson.annotations.SerializedName

data class ResponseMovieImages(
    @SerializedName("id")
    val movieId : Int,
    @SerializedName("backdrops")
    val backdrops : List<ResponseMovieImage>,
    @SerializedName("logos")
    val logos : List<ResponseMovieImage>,
    @SerializedName("posters")
    val posters : List<ResponseMovieImage>

) {
}