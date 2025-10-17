package com.example.movieapplication.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterRes: Int = 0,
    @SerializedName("poster_path")
    val poster_path: String = "",
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,
    @SerializedName("release_date")
    val releaseDate: String = ""
)
