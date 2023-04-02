package com.example.moviesapp.network.models

import com.example.moviesapp.ui.films.ui.FilmItem

data class ResponsePopularMovies(
    val error: String?,
    val results: List<FilmItemFromNetwork>?,
) {
    fun toFilmItemList(): List<FilmItem> {
        return results.orEmpty().map { it.toFilmItem() }
    }
}