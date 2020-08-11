package com.example.topmovies.model

import android.content.Context
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.topmovies.model.db.DatabaseFactory
import com.example.topmovies.model.network.APIFactory
import kotlinx.coroutines.Dispatchers

class MovieRepository(context: Context) {

    private val remote = APIFactory.tmdbDataSource
    private val dao = DatabaseFactory.db(context).moviesDao()

    var movies = liveData(Dispatchers.IO) {
        emit(Result.loading())
        val source = dao.getAll().map { Result.success(it.toDomain()) }
        emitSource(source)

        val responseStatus = remote.fetchData()
        if (responseStatus.status == Result.Status.SUCCESS) {
            dao.insertAll(responseStatus.data!!.results.toDB())
        } else if (responseStatus.status == Result.Status.ERROR) {
            emit(Result.error(responseStatus.message!!))
            emitSource(source)
        }
    }
}
