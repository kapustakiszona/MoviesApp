package com.example.moviesapp.ui.details.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.network.repository.FilmRepository
import com.example.moviesapp.ui.details.ui.TAG
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
            Log.d(TAG, "getDetailsStarted")
            FilmRepository.fetchDetails(filmID)
        }
    }


}