package com.example.moviesapp.network.models

import com.example.moviesapp.models.Actor
import com.example.moviesapp.ui.details.ui.ActorItem

data class ActorsListResponse(
    val cast: List<Cast>,
    val id: Int
)
    fun convertResponseToActorsList(result: List<Cast>): List<ActorItem> {
        return result.map {
            ActorItem(Actor(it.name, it.profile_path))
        }
    }

