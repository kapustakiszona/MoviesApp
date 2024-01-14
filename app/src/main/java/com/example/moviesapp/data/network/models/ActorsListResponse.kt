package com.example.moviesapp.data.network.models

import com.example.moviesapp.models.Actor
import com.google.gson.annotations.SerializedName

data class ActorsListResponse(
    val cast: List<Cast>?,
    val error: String?,
) {

    data class Cast(
        val name: String,
        @SerializedName("profile_path") val profilePath: String
    ) {
        fun toActor(): Actor =
            Actor(
                name = name,
                image = profilePath
            )
    }

    fun toActorsList(): List<Actor> {
        return cast.orEmpty().map {
            it.toActor()
        }
    }
}

