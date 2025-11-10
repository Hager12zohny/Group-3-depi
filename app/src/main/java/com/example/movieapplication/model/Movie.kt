package com.example.movieapplication.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
<<<<<<< HEAD
    val posterRes: Double = 0.00,
=======
    val posterRes: Double = 0,
>>>>>>> origin/main
    val poster_path: String,
    val voteAverage: Double = 0.0,
    val releaseDate: String = ""
)
