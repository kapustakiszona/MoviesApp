package com.example.moviesapp.network.models

import com.example.moviesapp.models.Chip
import com.example.moviesapp.ui.films.ui.ChipItem

data class GenreListResponse(
    val genres: List<Genre>?,
    override val error: String?,
): NetworkError {
    data class Genre(
        val id: Int,
        val name: String
    ) {
        fun toChipItem(): ChipItem {
            return ChipItem(
                chipItem = Chip(
                    id = id,
                    name = name
                )
            )
        }
        fun toChip(): Chip{
            return Chip(
                id = id,
                name = name
            )
        }
    }


    fun toChipList(): List<ChipItem> {
        return genres.orEmpty().map {
            it.toChipItem()
        }
    }
}

