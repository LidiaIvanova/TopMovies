package com.example.topmovies.model.network

data class TMDBMovieResponse(
    val results: List<TMDBMovieDTO>,
    val page: Int,
    val total_pages: Int
)