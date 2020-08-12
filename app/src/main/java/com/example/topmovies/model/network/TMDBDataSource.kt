package com.example.topmovies.model.network

class TMDBDataSource(private val service: TMDbService) : BaseDataSource() {
    suspend fun fetchData(page: Int) = getResult { service.getMoviesAsync(page) }
}

