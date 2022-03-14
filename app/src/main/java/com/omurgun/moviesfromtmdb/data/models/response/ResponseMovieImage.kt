package com.omurgun.moviesfromtmdb.data.models.response

import com.google.gson.annotations.SerializedName
import com.omurgun.moviesfromtmdb.data.models.entity.EntityMovieImageBackdrop
import com.omurgun.moviesfromtmdb.data.models.entity.EntityMovieImageLogo
import com.omurgun.moviesfromtmdb.data.models.entity.EntityMovieImagePoster

data class ResponseMovieImage(
    @SerializedName("height")
    val height : Int,
    @SerializedName("width")
    val width : Int,
    @SerializedName("file_path")
    val path : String

) {

    fun toEntityMovieImageBackdrop(id : Int,movieId : Int) : EntityMovieImageBackdrop {
        return EntityMovieImageBackdrop(
            id,
            movieId,
            this.height,
            this.width,
            this.path
        )
    }

    fun toEntityMovieImageLogo(id : Int,movieId : Int) : EntityMovieImageLogo {
        return EntityMovieImageLogo(
            id,
            movieId,
            this.height,
            this.width,
            this.path
        )
    }


    fun toEntityMovieImagePoster(id : Int,movieId : Int) : EntityMovieImagePoster {
        return EntityMovieImagePoster(
            id,
            movieId,
            this.height,
            this.width,
            this.path
        )
    }


}