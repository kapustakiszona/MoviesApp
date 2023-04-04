package com.example.moviesapp.network.models

import com.example.moviesapp.ui.films.ui.FilmItem

data class ResponsePopularFilms(
    val results: List<FilmItemFromNetwork>?,
    override val error: String?,
    ) : NetworkError {
    fun toFilmList(): List<FilmItem> {
        return results.orEmpty().map {
            it.toFilmItem()
        }
    }
}

