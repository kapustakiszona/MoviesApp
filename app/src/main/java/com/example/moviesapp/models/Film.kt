package com.example.moviesapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Film(
    val id: Long,
    val name: String,
    val date_publication: String,
    val rating: Float,
    val description: String,
    val photo: Int,
    val genre: String,
    val actors: ArrayList<Actor>
) : Parcelable {

    fun getActorsList(): ArrayList<Actor> = actors
}
