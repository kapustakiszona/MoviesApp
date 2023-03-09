package com.example.moviesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.databinding.ListItemBinding

class FilmAdapter(
    private var filmList: List<Film>,
    private val listener: AdapterListener
) :
    RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {


    class FilmViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film, listener: AdapterListener) = with(binding) {
            posterIv.setImageResource(film.photo)
            descriptionTv.text = film.description
            nameTitleTv.text = film.name
            ageRatingTv.text = film.id.toString()
            ratingBar.rating = film.rating.div(2)
            itemView.setOnClickListener {
                listener.onClick(film)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            ListItemBinding.inflate(
                inflater,
                parent,
                false
            )
        return FilmViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filmList.size
    }


    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(filmList[position], listener)
    }



}