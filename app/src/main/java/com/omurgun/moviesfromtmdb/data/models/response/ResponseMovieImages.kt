package com.omurgun.moviesfromtmdb.data.models.response

import com.google.gson.annotations.SerializedName

data class ResponseMovieImages(
    @SerializedName("id")
    val movieId : Int,
    @SerializedName("backdrops")
    val backdrops : ArrayList<ResponseMovieImageBackdrop>,
    @SerializedName("logos")
    val logos : ArrayList<ResponseMovieImageLogo>,
    @SerializedName("posters")
    val posters : ArrayList<ResponseMovieImagePoster>

) {
}