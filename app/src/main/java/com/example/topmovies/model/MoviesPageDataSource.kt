package com.example.topmovies.model

import androidx.paging.PageKeyedDataSource
import com.example.topmovies.model.domain.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


internal class MoviesPageDataSource(private val repository: MovieRepository) :
    PageKeyedDataSource<Int, Movie>() {

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.getData(params.key)

            callback.onResult(
                if (result.status == Result.Status.SUCCESS) result.data!! else listOf(),
                if (repository.getState().value is MovieRepository.State.EndOfList) null else params.key + 1
            )
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.getData(repository.getFirstPageNumber())

            callback.onResult(
                if (result.status == Result.Status.SUCCESS) result.data!! else listOf(), null,
                if (repository.getState().value is MovieRepository.State.EndOfList)
                    null else repository.getFirstPageNumber() + 1
            )
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.getData(params.key)

            callback.onResult(
                if (result.status == Result.Status.SUCCESS) result.data!! else listOf(),
                if (repository.getState().value is MovieRepository.State.EndOfList) null else params.key - 1
            )
        }
    }
}