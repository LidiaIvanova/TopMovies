package com.example.topmovies.viewmodel

import com.example.topmovies.model.network.APIFactory
import java.text.SimpleDateFormat
import java.util.*

fun getFormattedReleaseDate(date: Date?): String {
    return if (date != null)
        SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(date)
    else
        ""
}

fun getFormattedPosterPath(posterPath: String): String = APIFactory.API_IMAGE_PATH + posterPath