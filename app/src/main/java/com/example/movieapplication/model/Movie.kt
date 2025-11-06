package com.example.movieapplication.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterRes: Int = 0,
    val poster_path: String,
    val voteAverage: Double = 0.0,
    val releaseDate: String = ""
)
