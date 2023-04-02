package com.example.moviesapp.network.models

import com.example.moviesapp.models.Film
import com.example.moviesapp.network.Util
import com.example.moviesapp.ui.films.ui.FilmItem

data class ResponsePopularMovies(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
){

}

fun convertResponseToFilmList(results: List<Result>): List<FilmItem> {
    return results.map {
        FilmItem(
            Film(
                id = it.id,
                name = it.title,
                date_publication = it.release_date,
                description = it.overview,
                genreIds = it.genre_ids,
                genreName = "",
                photo = it.poster_path,
                rating = it.vote_average.div(2).toFloat(),
                adult = Util.getAdultOrNot(it.adult)
            )
        )
    }
}