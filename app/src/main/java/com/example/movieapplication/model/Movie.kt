package com.example.movieapplication.model

data class Movie(
    val id: Int,
    val title: String?,
    val poster_path: String?,
    val vote_average: Double?,
    val release_date: String?,
    val overview: String?
)
