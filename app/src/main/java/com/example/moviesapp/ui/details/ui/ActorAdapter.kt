package com.example.moviesapp.ui.details.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviesapp.databinding.ActorItemBinding
import com.example.moviesapp.models.Actor

class ActorAdapter(private val actorsList: ArrayList<Actor>) :
    RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    class ActorViewHolder(private val binding: ActorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(actor: Actor) = with(binding) {
            rvActorIv.load("https://image.tmdb.org/t/p/original/" + actor.image){
                crossfade(500)
            }
            rvNameTv.text = actor.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ActorItemBinding.inflate(inflater, parent, false)
        return ActorViewHolder(binding)
    }

    override fun getItemCount(): Int = actorsList.size

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bind(actorsList[position])
    }
}