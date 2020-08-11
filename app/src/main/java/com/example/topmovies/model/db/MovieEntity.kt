package com.example.topmovies.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey val id: Int,
    val posterPath: String,
    val voteAverage: Int,
    val title: String,
    val releaseDate: Long,
    val overview: String
)