package com.example.moviesapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesapp.network.getPopularMoviesAsync
import com.example.moviesapp.ui.films.ui.FilmItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object FilmsRepository {

    private val mFilmList = MutableLiveData<List<FilmItem>?>()
    val filmList: LiveData<List<FilmItem>?> = mFilmList

    private val mFilmListError = MutableLiveData<String?>()
    val filmListError: LiveData<String?> = mFilmListError

    suspend fun fetchFilms() = withContext(Dispatchers.IO) {
        val result = getPopularMoviesAsync()
        withContext(Dispatchers.Main) {
            result.error?.let {
                mFilmListError.value = it
            }
            result.results?.let {
                mFilmList.value = result.toFilmItemList()
            }
        }
    }

}