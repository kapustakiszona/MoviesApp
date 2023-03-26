package com.example.moviesapp.network.models

data class GenreListResponse(
    val genres: List<Genre>
) {

    data class Genre(
        val id: Int,
        val name: String
    )
}