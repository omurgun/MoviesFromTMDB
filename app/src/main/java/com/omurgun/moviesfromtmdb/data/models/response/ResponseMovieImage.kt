package com.omurgun.moviesfromtmdb.data.models.response

import com.google.gson.annotations.SerializedName

data class ResponseMovieImage(
    @SerializedName("height")
    val height : Int,
    @SerializedName("width")
    val width : Int,
    @SerializedName("file_path")
    val path : String

) {


}