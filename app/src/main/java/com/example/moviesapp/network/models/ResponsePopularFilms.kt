package com.example.moviesapp.network.models

import com.example.moviesapp.localdb.entities.FilmEntity
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
    fun toFilmListEntity(): List<FilmEntity>{
        return results.orEmpty().map {
            it.toFilmEntity()
        }
    }
}

