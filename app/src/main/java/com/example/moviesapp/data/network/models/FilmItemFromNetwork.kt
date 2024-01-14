package com.example.moviesapp.data.network.models

import com.example.moviesapp.data.localdb.entities.FilmEntity
import com.example.moviesapp.data.network.Util
import com.example.moviesapp.models.Film
import com.google.gson.annotations.SerializedName

data class FilmItemFromNetwork(
    val adult: Boolean?,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    val id: Int?,
    val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    val title: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
) {
    fun toFilm(): Film =
        Film(
            id = id ?: 0,
            name = title.orEmpty(),
            date_publication = releaseDate.orEmpty(),
            description = overview.orEmpty(),
            genre_id = genreIds.orEmpty().firstOrNull(),
            photo = posterPath.orEmpty(),
            rating = (voteAverage ?: 0.0).div(2).toFloat(),
            adult = Util.getAdultOrNot(adult == true)
        )

    fun toFilmEntity(): FilmEntity = FilmEntity(
        id = id ?: 0,
        name = title.orEmpty(),
        datePublication = releaseDate.orEmpty(),
        adult = Util.getAdultOrNot(adult == true),
        rating = (voteAverage ?: 0.0).div(2).toFloat(),
        description = overview.orEmpty(),
        photo = posterPath.orEmpty(),
        genreIds = genreIds.orEmpty().firstOrNull(),
        genreName = ""
    )
}
