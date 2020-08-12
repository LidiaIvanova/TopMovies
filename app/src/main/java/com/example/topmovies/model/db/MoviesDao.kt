package com.example.topmovies.model.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy


@Dao
interface MoviesDao {
    @Query("SELECT * FROM movieentity ORDER BY releaseDate ASC")
    fun getAllMovies(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movieentity WHERE page = :page ORDER BY releaseDate ASC")
    fun getMoviesFromPage(page:Int): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movieEntities: List<MovieEntity>)


}