package com.example.moviesapp.network.models

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
)