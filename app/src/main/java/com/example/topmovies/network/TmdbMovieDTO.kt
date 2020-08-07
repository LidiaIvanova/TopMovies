package com.example.topmovies.network

data class TmdbMovieDTO(
    val id: Int,
    val vote_average: Double,
    val title: String,
    val overview: String,
    val adult: Boolean
)