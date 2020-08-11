package com.example.topmovies.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy


@Dao
interface MoviesDao {
    @Query("SELECT * FROM movieentity")
    fun getAll(): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movieEntities: List<MovieEntity>)
}