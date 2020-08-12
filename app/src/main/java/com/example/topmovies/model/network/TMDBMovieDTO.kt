package com.example.topmovies.model.network

data class TMDBMovieDTO(
    val id: Int,
    val poster_path: String?,
    val release_date: String?,
    val vote_average: Double?,
    val title: String?,
    val overview: String?
)