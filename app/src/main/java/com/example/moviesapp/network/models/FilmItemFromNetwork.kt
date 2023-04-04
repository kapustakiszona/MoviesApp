package com.example.moviesapp.network.models

import com.example.moviesapp.models.Film
import com.example.moviesapp.network.Util
import com.example.moviesapp.ui.films.ui.FilmItem

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
    fun toFilmItem(): FilmItem {
        return FilmItem(
            Film(
                id = id ?: 0,
                name = title.orEmpty(),
                date_publication = release_date.orEmpty(),
                description = overview.orEmpty(),
                genreIds = genre_ids.orEmpty(),
                genreName = "",
                photo = poster_path.orEmpty(),
                rating = (vote_average ?: 0.0).div(2).toFloat(),
                adult = Util.getAdultOrNot(adult == true)
            )
        )
    }
}
