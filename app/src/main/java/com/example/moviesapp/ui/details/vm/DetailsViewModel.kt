package com.example.moviesapp.ui.details.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.repository.FilmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val filmRepository: FilmRepository
) : ViewModel() {

    val actorLiveData = filmRepository.actorList
    val actorError = filmRepository.actorListError

    val filmLiveData = filmRepository.filmDetails
    val filmError = filmRepository.filmDetailsError

    fun onInitialized(filmID: Int) {
        viewModelScope.launch {
            filmRepository.fetchActors(filmID)
            filmRepository.fetchDetails(filmID)
        }
    }

    override fun onCleared() {
        filmRepository.clearDetailsLiveData()
        super.onCleared()
    }

}