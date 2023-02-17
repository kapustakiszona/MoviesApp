package com.example.moviesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.databinding.ListItemBinding

class FilmAdapter(private val filmList: List<Film>) :
    RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {


    class FilmViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

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
        val myCurrentItem = filmList[position]
        with(holder.binding) {
            imageTitleIv.setImageResource(myCurrentItem.photo)
            datePublicationTv.text = myCurrentItem.date_publication
            nameTitleTv.text = myCurrentItem.name
        }
        holder.itemView.setOnClickListener {
            it.findNavController()
                .navigate(FilmFragmentDirections.actionFilmFragmentToDetailsFragment(myCurrentItem))
        }
    }

}