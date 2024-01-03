package com.example.moviesapp.data.network.models

import com.example.moviesapp.models.Chip

data class GenreListResponse(
    val genres: List<Genre>?,
    override val error: String?,
) : NetworkError {
    data class Genre(
        val id: Int,
        val name: String
    ) {
        fun toChip(): Chip {
            return Chip(
                id = id,
                name = name
            )
        }
    }


    fun toChipList(): List<Chip> {
        return genres.orEmpty().map {
            it.toChip()
        }
    }
}

