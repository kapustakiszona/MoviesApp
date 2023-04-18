package com.example.moviesapp.ui.details.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.network.repository.FilmRepository
import kotlinx.coroutines.launch


class DetailsViewModel : ViewModel() {

    val actorLiveData = FilmRepository.actorList
    val actorError = FilmRepository.actorListError

    val filmLiveData = FilmRepository.filmDetails
    val filmError =  FilmRepository.filmDetailsError

    fun onInitialized(filmID: Int){
        viewModelScope.launch {
            FilmRepository.fetchActors(filmID)
        }
        viewModelScope.launch {
            FilmRepository.fetchDetails(filmID)
        }
    }


}