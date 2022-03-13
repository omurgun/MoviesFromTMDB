package com.omurgun.moviesfromtmdb.data.models.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.omurgun.moviesfromtmdb.data.models.internal.InternalFavoriteMovie

@Entity(tableName = "movies")
data class ResponseMovie(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id : Int,
    @ColumnInfo(name = "title")
    @SerializedName("original_title")
    var title : String,
    @ColumnInfo(name = "description")
    @SerializedName("overview")
    var description : String,
    @ColumnInfo(name = "releaseDate")
    @SerializedName("release_date")
    var releaseDate : String,
    @ColumnInfo(name = "averageVote")
    @SerializedName("vote_average")
    var averageVote : Float,
    @ColumnInfo(name = "posterPath")
    @SerializedName("poster_path")
    var posterPath : String,

    ) {

    fun toInternalFavoriteMovie() : InternalFavoriteMovie{
        return InternalFavoriteMovie(
            this.id,
            this.title,
            this.description,
            this.releaseDate,
            this.averageVote,
            this.posterPath
        )
    }
}