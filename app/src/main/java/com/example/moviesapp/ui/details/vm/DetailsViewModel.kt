package com.example.moviesapp.ui.details.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.models.Film
import com.example.moviesapp.network.NetworkClient
import com.example.moviesapp.network.models.DetailResponse
import com.example.moviesapp.network.models.convertResponseToActorsList
import com.example.moviesapp.network.models.convertResponseToFilmDetails
import com.example.moviesapp.ui.details.ui.ActorItem
import com.example.moviesapp.ui.details.ui.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailsViewModel : ViewModel() {
    private val _actors = MutableLiveData<List<ActorItem>>()
    val actors: LiveData<List<ActorItem>> = _actors

    private val _film = MutableLiveData<Film>()
    val film: LiveData<Film> = _film

    fun getActorsListById(filmID: Int) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                NetworkClient.create().getActorsListById(filmID)
            }
                _actors.value = convertResponseToActorsList(result.cast)
        }
    }

    fun getFilmDetailsById(filmID: Int) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    NetworkClient.create().getMovieDetailsById(filmID)
                } catch (e: Exception) {
                    Log.d(TAG, "DetailsRequest connection error: $e")
                }
            }
            _film.value = convertResponseToFilmDetails(result as DetailResponse)
        }
    }
}