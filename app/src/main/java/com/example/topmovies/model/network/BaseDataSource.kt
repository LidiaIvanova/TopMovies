package com.example.topmovies.model.network

import android.util.Log
import com.example.topmovies.model.Result
import kotlinx.coroutines.Deferred
import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: () -> Deferred<Response<T>>): Result<T> {
        try {
            val response = call().await()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Result<T> {
        Log.e(this.javaClass.canonicalName ?: "", message)
        return Result.error("Network call has failed for a following reason: $message")
    }

}