package com.example.topmovies.model.network

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDbService {
    @GET("discover/movie?primary_release_year=2020")
    fun getMoviesAsync(@Query("page") page: Int): Deferred<Response<TmdbMovieResponse>>
}