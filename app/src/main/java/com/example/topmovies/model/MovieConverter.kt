package com.example.topmovies.model

import com.example.topmovies.model.db.MovieEntity
import com.example.topmovies.model.domain.Movie
import com.example.topmovies.model.network.TMDBMovieDTO
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

internal fun TMDBMovieDTO.toDB(page: Int) = MovieEntity(
    id = id,
    posterPath = poster_path ?: "",
    voteAverage = if (vote_average == null) 0 else (vote_average * 10).toInt(),
    title = title ?: "",
    releaseDate = stringDateToLong(release_date),
    overview = overview ?: "",
    page = page
)

internal fun List<TMDBMovieDTO>.toDB(page: Int) = this.map { it.toDB(page) }

internal fun List<MovieEntity>.toDomain() = this.map {
    Movie(
        id = it.id,
        posterPath = it.posterPath,
        voteAverage = it.voteAverage,
        title = it.title,
        releaseDate = longDateToDate(it.releaseDate),
        overview = it.overview,
        page = it.page
    )
}

private fun stringDateToLong(string: String?): Long {

    if (string == null) {
        return 0
    }
    return try {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(string)
        date?.time ?: 0
    } catch (error: ParseException) {
        0
    }
}

private fun longDateToDate(long: Long): Date? {
    if (long == 0L)
        return null
    return Date(long)
}

