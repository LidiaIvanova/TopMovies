package com.example.topmovies.model.db

import android.content.Context
import androidx.room.Room


object DatabaseFactory {

    private const val DATABASE_NAME = "MoviesDatabase"

    fun db(context: Context): MoviesDatabase =
        Room.databaseBuilder<MoviesDatabase>(
            context,
            MoviesDatabase::class.java, DATABASE_NAME
        ).build()
}