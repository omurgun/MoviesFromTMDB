package com.omurgun.moviesfromtmdb.data.models.request

data class RequestSearchMovie (
    val query : String,
    val currentPageCount : Int
        )