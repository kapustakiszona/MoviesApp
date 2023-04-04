package com.example.moviesapp.network.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesapp.models.Film
import com.example.moviesapp.network.getActorListByIdAsync
import com.example.moviesapp.network.getGenreListAsync
import com.example.moviesapp.network.getMovieDetailsByIdAsync
import com.example.moviesapp.network.getPopularMoviesAsync
import com.example.moviesapp.ui.details.ui.ActorItem
import com.example.moviesapp.ui.films.ui.ChipItem
import com.example.moviesapp.ui.films.ui.FilmItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object FilmRepository {
    private val mChipList = MutableLiveData<List<ChipItem>?>()
    val chipList: LiveData<List<ChipItem>?> = mChipList

    private val mChipListError = MutableLiveData<String?>()
    val chipListError: LiveData<String?> = mChipListError

    private val mFilmList = MutableLiveData<List<FilmItem>?>()
    val filmList: LiveData<List<FilmItem>?> = mFilmList

    private val mFilmListError = MutableLiveData<String?>()
    val filmListError: LiveData<String?> = mFilmListError

    private val mActorList = MutableLiveData<List<ActorItem>?>()
    val actorList: LiveData<List<ActorItem>?> = mActorList

    private val mActorListError = MutableLiveData<String?>()
    val actorListError: LiveData<String?> = mActorListError

    private val mFilmDetails = MutableLiveData<Film?>()
    val filmDetails: LiveData<Film?> = mFilmDetails

    private val mFilmDetailsError = MutableLiveData<String?>()
    val filmDetailsError: LiveData<String?> = mFilmDetailsError

    fun clearLiveData(){
        mFilmDetails.value = null
    }

    suspend fun fetchDetails(filmId: Int) = withContext(Dispatchers.IO) {
        val result = getMovieDetailsByIdAsync(filmId)
        withContext(Dispatchers.Main) {
            result.error?.let { errorMessage ->
                mFilmDetailsError.value = errorMessage
            }
            result.let {
                mFilmDetails.value = result.toFilmDetails()
            }
        }
    }

    suspend fun fetchActors(filmId: Int) = withContext(Dispatchers.IO) {
        val result = getActorListByIdAsync(filmId)
        withContext(Dispatchers.Main) {
            result.error?.let { errorMessage ->
                mActorListError.value = errorMessage
            }
            result.cast?.let {
                mActorList.value = result.toActorsList()
            }
        }
    }

    suspend fun fetchChips() = withContext(Dispatchers.IO) {
        val result = getGenreListAsync()
        withContext(Dispatchers.Main) {
            result.error?.let { errorMessage ->
                mChipListError.value = errorMessage
            }
            result.genres?.let {
                mChipList.value = result.toChipList()
            }
        }
    }

    suspend fun fetchFilms() = withContext(Dispatchers.IO) {
        val result = getPopularMoviesAsync()
        withContext(Dispatchers.Main) {
            result.error?.let { errorMessage ->
                mFilmListError.value = errorMessage
            }
            result.results?.let {
                mFilmList.value = result.toFilmList()
            }
        }
    }

    fun toggleChipsState(item: ChipItem) {// Переключаем статус чипсов
        val oldChipsList = chipList.value.orEmpty()
        oldChipsList.find { it == item }?.chipItem?.state = !item.chipItem.state
        mChipList.value = oldChipsList
    }

}