package com.example.moviesapp.data.network.models

import com.example.moviesapp.data.network.Util
import com.example.moviesapp.models.Film
import com.google.gson.annotations.SerializedName

data class FilmDetailsResponse(
    val adult: Boolean?,
    val genres: List<GenreListResponse.Genre>?,
    val id: Int?,
    val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    val title: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    val error: String?,
) {

    fun toFilmDetails(): Film {
        return Film(
            id = id ?: 0,
            name = title.orEmpty(),
            date_publication = releaseDate.orEmpty(),
            description = overview.orEmpty(),
            genre_id = 0,
            genre_name = genres.orEmpty().firstOrNull()?.name.orEmpty(),
            photo = posterPath.orEmpty(),
            rating = (voteAverage ?: 0.0).div(2).toFloat(),
            adult = Util.getAdultOrNot(adult == true)
        )
    }
}