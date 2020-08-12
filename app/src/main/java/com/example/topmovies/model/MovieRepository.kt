package com.example.topmovies.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.topmovies.model.db.DatabaseFactory
import com.example.topmovies.model.domain.Movie
import com.example.topmovies.model.network.TMDBAPIFactory

class MovieRepository(context: Context) {

    private val remote = TMDBAPIFactory.tmdbDataSource
    private val dao = DatabaseFactory.db(context).moviesDao()
    private val state: MutableLiveData<State> = MutableLiveData(State.Loading)

    suspend fun getData(page: Int): Result<List<Movie>> {

        val result = dao.getMoviesFromPage(page)

        if (result.isNotEmpty()) {
            state.postValue(State.HasData)
            return Result(Result.Status.SUCCESS, result.toDomain(), null)
        }

        state.postValue(State.Loading)

        val responseStatus = remote.fetchData(page)

        return if (responseStatus.status == Result.Status.SUCCESS) {
            val newMovies = responseStatus.data!!.results.toDB(responseStatus.data.page)
            dao.insertAll(newMovies)
            if (responseStatus.data.page < responseStatus.data.total_pages)
                state.postValue(State.HasData)
            else
                state.postValue(State.EndOfList)

            Result(Result.Status.SUCCESS, newMovies.toDomain(), null)
        } else {
            state.postValue(State.Error(responseStatus.message ?: ""))
            Result(Result.Status.ERROR, null, responseStatus.message)
        }
    }

    fun getPageSize(): Int {
        return TMDBAPIFactory.PAGE_SIZE
    }

    fun getFirstPageNumber(): Int {
        return TMDBAPIFactory.FIRST_PAGE_NUMBER
    }

    fun getState(): LiveData<State> {
        return state
    }

    sealed class State {
        object HasData : State()
        object Loading : State()
        object EndOfList : State()
        class Error(val message: String): State()
    }
}
