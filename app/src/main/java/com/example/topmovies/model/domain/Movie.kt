package com.example.topmovies.model.domain

import java.util.*


data class Movie(
    val id: Int,
    val posterPath: String,
    val voteAverage: Int,
    val title: String,
    val releaseDate: Date?,
    val overview: String,
    val page: Int
)