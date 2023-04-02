package com.example.moviesapp.network.models

import com.example.moviesapp.models.Chip
import com.example.moviesapp.ui.films.ui.ChipItem

data class GenreListResponse(
    val genres: List<Genre>
) {

    data class Genre(
        val id: Int,
        val name: String
    )
}

fun convertResponseToChipList(result: List<GenreListResponse.Genre>): List<ChipItem> {
    return result.map {
        ChipItem(
            Chip(
                id = it.id,
                name = it.name,
                state = false
            )
        )
    }
}