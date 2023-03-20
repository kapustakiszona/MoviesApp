package com.example.moviesapp.network

data class ResponsePopularMovies(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)