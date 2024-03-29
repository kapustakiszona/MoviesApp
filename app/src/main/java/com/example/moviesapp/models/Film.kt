package com.example.moviesapp.models

import com.example.moviesapp.data.network.Constants.BASE_IMAGE_URL


data class Film(
    val id: Int,
    val name: String,
    val date_publication: String,
    val adult: String,
    val rating: Float,
    val description: String,
    val photo: String,
    val genre_id: Int?,
    var genre_name: String = ""
){

    fun getImageUrl(): String {
        return BASE_IMAGE_URL + photo
    }
}