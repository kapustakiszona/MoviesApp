package com.example.moviesapp.ui.details.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.network.repository.FilmRepository
import com.example.moviesapp.ui.details.ui.TAG
import kotlinx.coroutines.launch


class DetailsViewModel : ViewModel() {

    val _actors = FilmRepository.actorList
    val actorError = FilmRepository.actorListError

    val _film = FilmRepository.filmDetails
    val filmError =  FilmRepository.filmDetailsError

    fun getActorsListById(filmID: Int) {
        viewModelScope.launch {
            FilmRepository.fetchActors(filmID)
        }
    }

    fun getFilmDetailsById(filmID: Int) {
        viewModelScope.launch {
            Log.d(TAG, "getDetailsStarted")
            FilmRepository.fetchDetails(filmID)
        }
    }

}