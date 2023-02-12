package com.example.moviesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FilmAdapter(private val filmList: List<Film>) :
    RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {

    class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleNameTv: TextView = itemView.findViewById(R.id.name_title_tv)
        val posterImageIv: ImageView = itemView.findViewById(R.id.image_title_iv)
        val ratingTv: TextView = itemView.findViewById(R.id.rating_tv)
        val dateTv: TextView = itemView.findViewById(R.id.date_publication_tv)
        val descriptionTv: TextView = itemView.findViewById(R.id.description_tv)
        val idTextView: TextView = itemView.findViewById(R.id.id_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item,
                parent,
                false
            )
        return FilmViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val myCurrentItem = filmList[position]
        holder.dateTv.setText(myCurrentItem.date_publication)
        holder.descriptionTv.setText(myCurrentItem.description)
        holder.titleNameTv.setText(myCurrentItem.name)
        holder.ratingTv.setText(myCurrentItem.rating)
        holder.posterImageIv.setImageResource(myCurrentItem.photo)
        holder.idTextView.setText(myCurrentItem.id)
    }

}