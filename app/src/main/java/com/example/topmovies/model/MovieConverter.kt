package com.example.topmovies.model

import com.example.topmovies.model.db.MovieEntity
import com.example.topmovies.model.domain.Movie
import com.example.topmovies.model.network.TmdbMovieDTO
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

internal fun TmdbMovieDTO.toDB() = MovieEntity(
    id = id,
    posterPath = poster_path,
    voteAverage = (vote_average * 10).toInt(),
    title = title,
    releaseDate = stringDateToLong(release_date),
    overview = overview
)

internal fun List<TmdbMovieDTO>.toDB() = this.map { it.toDB() }

internal fun List<MovieEntity>.toDomain() = this.map {
    Movie(id = it.id,
        posterPath = it.posterPath,
        voteAverage = it.voteAverage,
        title = it.title,
        releaseDate = longDateToDate(it.releaseDate),
        overview = it.overview)
}

private fun stringDateToLong(string: String): Long {

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

