package com.example.moviesapp.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

class Dto {
    @Parcelize
    data class Request(@SerializedName("id") val id: Int) : Parcelable

    @Parcelize
    data class Response(@SerializedName("id") val id: Int) : Parcelable
}