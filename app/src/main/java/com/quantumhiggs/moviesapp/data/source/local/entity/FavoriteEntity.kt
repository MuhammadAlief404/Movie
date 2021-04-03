package com.quantumhiggs.moviesapp.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_favorite")
data class FavoriteEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var movieId: Int?,

    @ColumnInfo(name = "movie_name")
    var movieName: String?,

    @ColumnInfo(name = "movie_desc")
    var movieDesc: String?,

    @ColumnInfo(name = "movie_image")
    var movieImage: String?,

    @ColumnInfo(name = "movie_rating")
    var movieRating: String?,

    @ColumnInfo(name = "movie_release_date")
    var movieReleaseDate: String?,

    @ColumnInfo(name = "movie_language")
    var movieLanguage: String?,

    @ColumnInfo(name = "duration")
    var duration: String? = "",

    @ColumnInfo(name = "genre")
    var genre: String? = "",

    @ColumnInfo(name = "homepage")
    var homepage: String? = ""
)