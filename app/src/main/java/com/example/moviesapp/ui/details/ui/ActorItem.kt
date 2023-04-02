package com.example.moviesapp.ui.details.ui

import android.view.View
import coil.load
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ActorItemBinding
import com.example.moviesapp.models.Actor
import com.example.moviesapp.ui.adapter.BaseListItem

class ActorItem(private val actor: Actor) : BaseListItem {
    override fun getViewId(): Int = R.layout.actor_item

    override fun renderView(view: View, positionInAdapter: Int) {
        with(ActorItemBinding.bind(view)) {
            rvActorIv.load(actor.getImageUrl()) {
                crossfade(500)
            }
            rvNameTv.text = actor.name
        }
    }
}