package com.example.moviesapp.ui.films.ui

import android.view.View
import coil.load
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ListItemBinding
import com.example.moviesapp.models.Film
import com.example.moviesapp.network.BASE_IMAGE_URL
import com.example.moviesapp.ui.adapter.BaseListItem

class FilmItem(val film: Film) : BaseListItem {
    override fun getViewId(): Int = R.layout.list_item

    override fun renderView(view: View, positionInAdapter: Int) = with(ListItemBinding.bind(view)) {
        posterIv.load(film.getImageUrl(BASE_IMAGE_URL)) {
            crossfade(500)
        }
        descriptionTv.text = film.description
        nameTitleTv.text = film.name
        ageRatingTv.text = film.adult
        ratingBar.rating = film.rating
    }
}