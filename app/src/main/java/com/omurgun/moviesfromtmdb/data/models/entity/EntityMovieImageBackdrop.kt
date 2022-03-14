package com.omurgun.moviesfromtmdb.data.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovieImage

@Entity(tableName = "backdrops")
data class EntityMovieImageBackdrop(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int,
    @ColumnInfo(name = "movieId")
    val movieId : Int,
    @ColumnInfo(name = "height")
    val height : Int,
    @ColumnInfo(name = "width")
    val width : Int,
    @ColumnInfo(name = "path")
    val path : String

    ) {

    fun toResponseMovieImage() : ResponseMovieImage {
        return ResponseMovieImage(
            this.height,
            this.width,
            this.path
        )
    }

}