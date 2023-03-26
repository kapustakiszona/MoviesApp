package com.example.moviesapp.ui.films.ui

import com.example.moviesapp.models.Chip
import com.example.moviesapp.models.Film

interface AdapterListener {
    fun onFilmSelected(film: Film)

    fun onChipSelected(myChip: Chip)


}