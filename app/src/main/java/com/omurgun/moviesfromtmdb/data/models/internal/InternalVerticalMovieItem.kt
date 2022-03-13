package com.omurgun.moviesfromtmdb.data.models.internal

import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovieImage

data class InternalVerticalMovieItem(
    val titleItem : InternalTitleItem,
    val horizontalMovieImageItems: List<InternalHorizontalMovieImageItem>,
    val movieImages : List<ResponseMovieImage>
)