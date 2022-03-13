package com.omurgun.moviesfromtmdb.data.models.internal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie

@Entity(tableName = "favoriteMovies")
data class InternalFavoriteMovie(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id : Int,
    @ColumnInfo(name = "title")
    var title : String,
    @ColumnInfo(name = "description")
    var description : String,
    @ColumnInfo(name = "releaseDate")
    var releaseDate : String,
    @ColumnInfo(name = "averageVote")
    var averageVote : Float,
    @ColumnInfo(name = "posterPath")
    var posterPath : String,

    ) {

    fun toResponseMovie() : ResponseMovie{
        return ResponseMovie(
            this.id,
            this.title,
            this.description,
            this.releaseDate,
            this.averageVote,
            this.posterPath
        )
    }
}