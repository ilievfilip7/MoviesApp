package com.example.moviesapplication.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

/**
 * Represents one item movie.
 *
 * @property id The id of the movie.
 * @property title The title of the movie.
 * @property posterPath The image poster of the movie.
 * @property voteAverage The vote average of the movie.
 * @property overview Short description of the movie.
 */
@Parcelize
data class Movie(
    val id: Int?,
    val title: String?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "vote_average") val voteAverage: Float?,
    val overview: String?
) : Parcelable