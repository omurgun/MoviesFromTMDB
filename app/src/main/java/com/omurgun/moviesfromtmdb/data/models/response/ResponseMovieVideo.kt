package com.omurgun.moviesfromtmdb.data.models.response

import com.google.gson.annotations.SerializedName

data class ResponseMovieVideo (
    @SerializedName("id")
    val movieId : Int,
    @SerializedName("results")
    val results : List<ResponseVideo>,

        )

data class ResponseVideo(
    val name : String,
    val key : String,
    val site : String,
    val type : String
)