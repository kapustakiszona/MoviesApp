package com.example.moviesapp.data.network.models

import com.example.moviesapp.models.Film
import com.example.moviesapp.data.network.Util

data class FilmDetailsResponse(
    val adult: Boolean?,
    val genres: List<GenreListResponse.Genre>?,
    val id: Int?,
    val overview: String?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val vote_average: Double?,
    override val error: String?,
) : NetworkError {

    fun toFilmDetails(): Film {
        return Film(
            id = id ?: 0,
            name = title.orEmpty(),
            date_publication = release_date.orEmpty(),
            description = overview.orEmpty(),
            genre_id = 0,
            genre_name = genres.orEmpty().firstOrNull()?.name.orEmpty(),
            photo = poster_path.orEmpty(),
            rating = (vote_average ?: 0.0).div(2).toFloat(),
            adult = Util.getAdultOrNot(adult == true)
        )
    }
}