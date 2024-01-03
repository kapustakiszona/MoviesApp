package com.example.moviesapp.data.network.models

import com.example.moviesapp.data.localdb.entities.FilmEntity
import com.example.moviesapp.models.Film

data class ResponsePopularFilms(
    val results: List<FilmItemFromNetwork>?,
    override val error: String?,
    ) : NetworkError {
    fun toFilmList(): List<Film> {
        return results.orEmpty().map {
            it.toFilm()
        }
    }
    fun toFilmListEntity(): List<FilmEntity>{
        return results.orEmpty().map {
            it.toFilmEntity()
        }
    }
}

