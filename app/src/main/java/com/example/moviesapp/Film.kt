package com.example.moviesapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film(
    val id: Long,
    val name: String,
    val date_publication: String,
    val rating: Int,
    val description: String,
    val photo: Int,
) : Parcelable