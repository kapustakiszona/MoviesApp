package com.example.moviesapp.network

import com.example.moviesapp.network.models.ActorsListResponse
import com.example.moviesapp.network.models.FilmDetailsResponse
import com.example.moviesapp.network.models.GenreListResponse
import com.example.moviesapp.network.models.ResponsePopularFilms
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getPopularMoviesAsync() = withContext(Dispatchers.IO) {
    try {
        NetworkClient.filmsService.getPopularMovies()
    } catch (exc: Exception) {
        return@withContext ResponsePopularFilms(
            results = null,
            error = exc.message
        )
    }
}

suspend fun getGenreListAsync() = withContext(Dispatchers.IO) {
    try {
        NetworkClient.filmsService.getGenreList()
    } catch (exc: Exception) {
        return@withContext GenreListResponse(
            genres = null,
            error = exc.message
        )
    }
}

suspend fun getActorListByIdAsync(filmId: Int) = withContext(Dispatchers.IO) {
    try {
        NetworkClient.filmsService.getActorsListById(filmId)
    } catch (exc: Exception) {
        return@withContext ActorsListResponse(
            cast = null,
            error = exc.message
        )
    }
}

suspend fun getMovieDetailsByIdAsync(filmId: Int) = withContext(Dispatchers.IO) {
    try {
        NetworkClient.filmsService.getMovieDetailsById(filmId)
    } catch (exc: Exception) {
        return@withContext FilmDetailsResponse(
            id = null,
            title = null,
            adult = null,
            genres = null,
            overview = null,
            poster_path = null,
            release_date = null,
            vote_average = null,
            error = exc.message
        )
    }
}