package com.example.topmovies.model.network

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDbService {
    @GET("discover/movie?primary_release_year=2020&sort_by=primary_release_date.asc&vote_average.gte=5")
    fun getMoviesAsync(@Query("page") page: Int): Deferred<Response<TMDBMovieResponse>>
}