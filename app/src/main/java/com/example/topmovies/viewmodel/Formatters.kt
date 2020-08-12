package com.example.topmovies.viewmodel

import com.example.topmovies.model.network.TMDBAPIFactory
import java.text.SimpleDateFormat
import java.util.*

fun getFormattedReleaseDate(date: Date?): String {
    return if (date != null) {
        if (Locale.getDefault().toString() == "ru_RU")
            SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date)
        else
            SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(date)
    } else {
        ""
    }
}

fun getFormattedPosterPath(posterPath: String): String {
    return if (posterPath.isEmpty())
        posterPath
    else TMDBAPIFactory.API_IMAGE_PATH + posterPath
}