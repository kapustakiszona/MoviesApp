package com.example.moviesapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Actor(val name: String, val image: Int): Parcelable {

}
