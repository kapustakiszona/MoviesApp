package com.example.moviesapp.network.models

import com.example.moviesapp.models.Film
import com.example.moviesapp.network.Util

data class DetailResponse(
    val adult: Boolean,
    val backdrop_path: String,
    val genres: List<GenreListResponse.Genre>,
    val id: Int,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
) {

    fun convertResponseToFilmDetails(result: DetailResponse): Film {

        return Film(
            id = result.id,
            name = result.title,
            date_publication = result.release_date,
            description = result.overview,
            genreIds = emptyList(),
            genreName = result.genres[0].name,
            photo = result.poster_path,
            rating = result.vote_average.div(2).toFloat(),
            adult = Util.getAdultOrNot(result.adult)
        )
    }

}
