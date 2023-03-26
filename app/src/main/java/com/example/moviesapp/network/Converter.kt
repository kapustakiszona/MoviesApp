package com.example.moviesapp.network

import com.example.moviesapp.models.Actor
import com.example.moviesapp.models.Chip
import com.example.moviesapp.models.Film
import com.example.moviesapp.network.models.Cast
import com.example.moviesapp.network.models.DetailResponse
import com.example.moviesapp.network.models.GenreListResponse
import com.example.moviesapp.network.models.Result

class Converter {

    fun convertResponseToChipList(result: List<GenreListResponse.Genre>): ArrayList<Chip> {
        val chipsList = ArrayList<Chip>()
        for (item in result) {
            val chip = Chip(
                id = item.id,
                name = item.name
            )
            chipsList.add(chip)
        }
        return chipsList
    }

    fun convertResponseToActorsList(result: List<Cast>): ArrayList<Actor> {
        val actorsList = ArrayList<Actor>()
        for (item in result) {
            val actor = Actor(
                name = item.name,
                image = item.profile_path
            )
            actorsList.add(actor)
        }
        return actorsList
    }

    fun convertResponseToFilmList(results: List<Result>): ArrayList<Film> {
        val films = ArrayList<Film>()
        for (result in results) {
            val film = Film(
                id = result.id,
                name = result.title,
                date_publication = result.release_date,
                description = result.overview,
                genreIds = result.genre_ids,
                genreName = "",
                photo = result.poster_path,
                rating = result.vote_average.div(2).toFloat(),
                adult = getAdultOrNotInfo(result.adult)
            )
            films.add(film)
        }
        return films
    }

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
            adult = getAdultOrNotInfo(result.adult)
        )
    }

    private fun getAdultOrNotInfo(adult: Boolean) = if (adult) "+18" else "+14"


}