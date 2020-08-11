package com.example.topmovies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.topmovies.model.MovieRepository
import com.example.topmovies.model.Result
import com.example.topmovies.model.db.MovieEntity
import com.example.topmovies.model.domain.Movie

class MoviesViewModel(app: Application): AndroidViewModel(app) {
    private val movies: LiveData<Result<List<Movie>>> = MovieRepository(getApplication()).movies


    fun getMovies(): LiveData<Result<List<Movie>>> {
        return movies
    }
    fun onMovieScheduleButtonClick() {
        //TODO
    }
}