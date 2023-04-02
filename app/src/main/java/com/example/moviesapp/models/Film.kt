package com.example.moviesapp.models



data class Film(
    val id: Int,
    val name: String,
    val date_publication: String,
    val adult: String,
    val rating: Float,
    val description: String,
    val photo: String,
    val genreIds: List<Int>,
    val genreName: String
){
    fun getImageUrl(baseUrl: String): String {
        return baseUrl + photo
    }
}