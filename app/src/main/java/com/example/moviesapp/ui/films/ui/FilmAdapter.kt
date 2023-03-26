package com.example.moviesapp.ui.films.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviesapp.databinding.ListItemBinding
import com.example.moviesapp.models.Film

class FilmAdapter(
    var filmList: List<Film>,
    private val listener: AdapterListener
) :
    RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {


    class FilmViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film, listener: AdapterListener) = with(binding) {
            posterIv.load("https://image.tmdb.org/t/p/original/" + film.photo){
                crossfade(500)
            }
            Log.d("FilmFragment", "image is: ${posterIv.drawable}")
            descriptionTv.text = film.description
            nameTitleTv.text = film.name
            ageRatingTv.text = film.adult
            ratingBar.rating = film.rating
            itemView.setOnClickListener {
                listener.onFilmSelected(film)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmAdapter.FilmViewHolder {
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