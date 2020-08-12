package com.example.topmovies.model.network

import com.example.topmovies.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object TMDBAPIFactory {

    private const val API_KEY = BuildConfig.TMDB_API_KEY
    private const val API_BASE_URL = "https://api.themoviedb.org/3/"
    const val API_IMAGE_PATH = "https://image.tmdb.org/t/p/w220_and_h330_face"
    const val PAGE_SIZE = 20
    const val FIRST_PAGE_NUMBER = 1

    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter(
                "api_key",
                API_KEY
            )
            .addQueryParameter("language", Locale.getDefault().toString().replace('_', '-'))
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val tmdbClient = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .build()


    private fun retrofit(): Retrofit = Retrofit.Builder()
        .client(tmdbClient)
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()


    val tmdbDataSource: TMDBDataSource = TMDBDataSource(
        retrofit()
            .create(TMDbService::class.java)
    )

}