package com.example.moviesapp.network

import com.example.moviesapp.network.models.ResponsePopularMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getPopularMoviesAsync() = withContext(Dispatchers.IO) {
    try {
        NetworkClient.filmsService.getPopularMovies()
    } catch (ex: Exception) {
        return@withContext ResponsePopularMovies(
            error = ex.message,
            results = null
        )
    }
}