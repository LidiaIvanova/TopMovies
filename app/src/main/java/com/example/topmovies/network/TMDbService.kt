package com.example.topmovies.network

import com.example.topmovies.BuildConfig
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

public interface TMDbService {
    @GET("discover/movie?primary_release_year=2020")
   fun getMovies(@Query("page") page: Int): Deferred<Response<TmdbMovieResponse>>
}