package com.example.moviesapp.network.models

import com.example.moviesapp.models.Actor

data class ActorsListResponse(
    val cast: List<Cast>?,
    override val error: String?,
) : NetworkError {

    data class Cast(
        val name: String,
        val profile_path: String
    ) {
        fun toActor(): Actor =
            Actor(
                name = name,
                image = profile_path
            )
    }

    fun toActorsList(): List<Actor> {
        return cast.orEmpty().map {
            it.toActor()
        }
    }
}

