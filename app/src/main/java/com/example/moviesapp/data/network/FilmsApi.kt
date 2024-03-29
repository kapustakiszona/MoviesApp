package com.example.moviesapp.data.network

import com.example.moviesapp.data.network.models.ActorsListResponse
import com.example.moviesapp.data.network.models.FilmDetailsResponse
import com.example.moviesapp.data.network.models.GenreListResponse
import com.example.moviesapp.data.network.models.ResponsePopularFilms
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FilmsApi @Inject constructor(private val filmsService: FilmsService) {
    suspend fun getPopularMoviesAsync() = withContext(Dispatchers.IO) {
        try {
            filmsService.getPopularMovies()
        } catch (exc: Exception) {
            return@withContext ResponsePopularFilms(
                results = null,
                error = exc.message
            )
        }
    }

    suspend fun getGenreListAsync() = withContext(Dispatchers.IO) {
        try {
            filmsService.getGenreList()
        } catch (exc: Exception) {
            return@withContext GenreListResponse(
                genres = null,
                error = exc.message
            )
        }
    }

    suspend fun getActorListByIdAsync(filmId: Int) = withContext(Dispatchers.IO) {
        try {
            filmsService.getActorsListById(filmId)
        } catch (exc: Exception) {
            return@withContext ActorsListResponse(
                cast = null,
                error = exc.message
            )
        }
    }

    suspend fun getMovieDetailsByIdAsync(filmId: Int) = withContext(Dispatchers.IO) {
        try {
            filmsService.getMovieDetailsById(filmId)
        } catch (exc: Exception) {
            return@withContext FilmDetailsResponse(
                id = null,
                title = null,
                adult = null,
                genres = null,
                overview = null,
                posterPath = null,
                releaseDate = null,
                voteAverage = null,
                error = exc.message
            )
        }
    }
}