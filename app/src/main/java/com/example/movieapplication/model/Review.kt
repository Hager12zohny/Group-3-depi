package com.example.movieapplication.model

data class Review(
    val id: String,
    val author: String?,
    val content: String?,
    val author_details: AuthorDetails?
)

data class AuthorDetails(
    val name: String?,
    val username: String?,
    val avatar_path: String?,
    val rating: Double?
)

