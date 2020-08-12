package com.example.topmovies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.topmovies.model.MovieRepository
import com.example.topmovies.model.MoviesPageDataSource
import com.example.topmovies.model.domain.Movie

class MoviesViewModel(app: Application) : AndroidViewModel(app) {

    private var adapter: MoviesAdapter
    private var repository = MovieRepository(getApplication())
    private var pagedList: LiveData<PagedList<Movie>>
    private val pagedListObserver: Observer<PagedList<Movie>>
    private var state = MutableLiveData<State>()
    private var repositoryStateObserver: Observer<MovieRepository.State>

    init {
        repositoryStateObserver = Observer {
            when (it) {
                is MovieRepository.State.HasData -> state.postValue(State.Success)
                is MovieRepository.State.EndOfList -> state.postValue(State.Success)
                is MovieRepository.State.Loading -> state.postValue(State.Loading)
                is MovieRepository.State.Error -> state.postValue(State.Error(it.message))
            }
        }
        repository.getState().observeForever(repositoryStateObserver)

        adapter = MoviesAdapter()

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(repository.getPageSize())
            .build()

        val dataSourceFactory = object : DataSource.Factory<Int, Movie>() {
            override fun create(): DataSource<Int, Movie> {
                return MoviesPageDataSource(repository)
            }
        }

        pagedList = LivePagedListBuilder<Int, Movie>(dataSourceFactory, config).build()
        pagedListObserver = Observer { adapter.submitList(it) }
        pagedList.observeForever(pagedListObserver)
    }

    private fun onMovieScheduleButtonClick() {
        //TODO
    }

    fun getMoviePagedAdapter(): MoviesAdapter {
        return adapter
    }

    fun getState(): LiveData<State> {
        return state
    }

    override fun onCleared() {
        pagedList.removeObserver(pagedListObserver)
        repository.getState().removeObserver(repositoryStateObserver)
        super.onCleared()
    }

    sealed class State {
        object Success : State()
        object Loading : State()
        class Error(val message: String) : State()
    }
}