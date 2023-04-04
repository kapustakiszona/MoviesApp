package com.example.moviesapp.network.models

import com.example.moviesapp.models.Actor
import com.example.moviesapp.ui.details.ui.ActorItem

data class ActorsListResponse(
    val cast: List<Cast>?,
    override val error: String?,
) : NetworkError {

    data class Cast(
        val name: String,
        val profile_path: String
    ) {
        fun toActorItem(): ActorItem {
            return ActorItem(
                actor = Actor(
                    name = name,
                    image = profile_path
                )
            )
        }
    }
    fun toActorsList(): List<ActorItem> {
        return cast.orEmpty().map {
            it.toActorItem()
        }
    }
}

