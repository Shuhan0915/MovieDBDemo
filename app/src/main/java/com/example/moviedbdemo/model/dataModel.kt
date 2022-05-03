package com.example.moviedbdemo.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/*
    Data class to hold latest movie list
 */
@Parcelize
data class MovieList(
    var page: Int,
    var results: List<Movie>,
    var totalPages: Int
) : Parcelable

/*
    Data class to hold certain movie
 */
@Parcelize
data class Movie(
    @SerializedName("id")
    var movieId: Int = 0,
    @SerializedName("homepage")
    var homepage: String = "",
    @SerializedName("vote_average")
    var voteAverage: String = "",
    @SerializedName("title")
    var title: String = "",
    @SerializedName("poster_path")
    var posterPath: String? = "",
) : Parcelable