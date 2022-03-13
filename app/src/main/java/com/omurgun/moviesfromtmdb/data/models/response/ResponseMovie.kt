package com.omurgun.moviesfromtmdb.data.models.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

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
}