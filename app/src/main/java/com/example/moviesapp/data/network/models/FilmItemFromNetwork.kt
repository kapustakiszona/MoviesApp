package com.example.moviesapp.data.network.models

import com.example.moviesapp.data.localdb.entities.FilmEntity
import com.example.moviesapp.models.Film
import com.example.moviesapp.data.network.Util

data class FilmItemFromNetwork(
    val adult: Boolean?,
    val genre_ids: List<Int>?,
    val id: Int?,
    val overview: String?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val vote_average: Double?,
) {
    fun toFilm(): Film =
        Film(
            id = id ?: 0,
            name = title.orEmpty(),
            date_publication = release_date.orEmpty(),
            description = overview.orEmpty(),
            genre_id = genre_ids.orEmpty().firstOrNull(),
            photo = poster_path.orEmpty(),
            rating = (vote_average ?: 0.0).div(2).toFloat(),
            adult = Util.getAdultOrNot(adult == true)
        )

    fun toFilmEntity(): FilmEntity = FilmEntity(
        id = id ?: 0,
        name = title.orEmpty(),
        datePublication = release_date.orEmpty(),
        adult = Util.getAdultOrNot(adult == true),
        rating = (vote_average ?: 0.0).div(2).toFloat(),
        description = overview.orEmpty(),
        photo = poster_path.orEmpty(),
        genreIds = genre_ids.orEmpty().firstOrNull(),
        genreName = ""
    )
}
