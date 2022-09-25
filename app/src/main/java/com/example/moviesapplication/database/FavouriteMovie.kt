package com.example.moviesapplication.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favourite_movies_table")
data class FavouriteMovie(
        @PrimaryKey
        val movieId: Long = 0L,
        @ColumnInfo(name = "title")
        val title: String = "",
        @ColumnInfo(name = "image_url")
        val imageUrl: String,
        @ColumnInfo(name = "vote_average")
        val voteAverage: Float = 0.0f,
        @ColumnInfo(name = "overview")
        val overview: String
) : Parcelable