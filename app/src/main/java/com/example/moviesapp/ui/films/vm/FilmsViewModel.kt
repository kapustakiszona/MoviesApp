package com.example.moviesapp.ui.films.vm

import android.util.Log
import androidx.lifecycle.*
import com.example.moviesapp.network.NetworkClient
import com.example.moviesapp.network.models.convertResponseToChipList
import com.example.moviesapp.network.models.convertResponseToFilmList
import com.example.moviesapp.ui.details.ui.TAG
import com.example.moviesapp.ui.films.ui.ChipItem
import com.example.moviesapp.ui.films.ui.FilmItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilmsViewModel : ViewModel() {
    init {
        setupChipList()
    }

    private val _chipList = MutableLiveData<List<ChipItem>>()
    val chipList: LiveData<List<ChipItem>> =
        _chipList  //тут храним акктуальный список чипсов с акктуальными статусами

    private val _searchQueryString = MutableLiveData<String>(null)
    val searchQueryString: LiveData<String> = _searchQueryString


    fun setSearchQuery(query: String) {
        _searchQueryString.value = query
    }

    private val _filmList = MutableLiveData<List<FilmItem>>()

    private val mFilteredFilmList = MediatorLiveData<List<FilmItem>>().apply {
        addSource(chipList) {
            value = mergeFilteredFilmList(
                chipsList = it,
                filmsList = _filmList.value,
                searchQuery = searchQueryString.value
            )
        }
        addSource(_filmList) {
            value = mergeFilteredFilmList(
                chipsList = chipList.value,
                filmsList = it,
                searchQuery = searchQueryString.value
            )
        }
        addSource(searchQueryString) {
            value = mergeFilteredFilmList(
                chipsList = chipList.value,
                filmsList = _filmList.value,
                searchQuery = it
            )
        }
    }

    val filteredFilmList: LiveData<List<FilmItem>> =
        mFilteredFilmList// Лайвдата которая торчит наружу

    private fun mergeFilteredFilmList(
        chipsList: List<ChipItem>?, filmsList: List<FilmItem>?, searchQuery: String?
    ): MutableList<FilmItem> {
        val listOfSelectedChips =
            chipsList.orEmpty().filter { it.chipItem.state }.map { it.chipItem.id }
        return filmsList.orEmpty()
            .filter {
                it.film.genreIds.isNotEmpty() && (listOfSelectedChips.isEmpty() || listOfSelectedChips.contains(
                    it.film.genreIds[0]
                ))
            }
            .filter { it.film.name.contains(searchQuery.orEmpty(), true) }
            .toMutableList()
    }


    fun setupChipList() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                NetworkClient.create().getGenreList()
            }
            _chipList.value = convertResponseToChipList(result.genres)
        }
    }


    fun setupFilmList() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                // Выполнить какую-то операцию в фоновом потоке
                NetworkClient.create().getPopularMovies()
            }
            _filmList.value = convertResponseToFilmList(result.results)
        }
    }

    fun toggleChipsState(item: ChipItem) {// Переключаем статус чипсов
        val oldChipsList = chipList.value.orEmpty()
        Log.d(TAG, "Oldchiplist size: ${oldChipsList.size}")
        oldChipsList.find { it == item }?.chipItem?.state = !item.chipItem.state
        _chipList.value = oldChipsList
    }
}
