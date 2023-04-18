package com.example.moviesapp.ui.films.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.network.repository.FilmRepository
import com.example.moviesapp.ui.films.ui.ChipItem
import com.example.moviesapp.ui.films.ui.FilmItem
import kotlinx.coroutines.launch

class FilmsViewModel : ViewModel() {
    init {
        setupChipList()
        setupFilmList()
    }

    val chipListLiveData = FilmRepository.chipList
    val chipError = FilmRepository.chipListError

    private val _searchQueryString = MutableLiveData<String>(null)
    private val searchQueryString: LiveData<String> = _searchQueryString

    val filmError = FilmRepository.filmListError

    fun setSearchQuery(query: String) {
        _searchQueryString.value = query
    }

    private val _filmList = FilmRepository.filmList

    private val mFilteredFilmList = MediatorLiveData<List<FilmItem>>().apply {
        addSource(chipListLiveData) {
            value = mergeFilteredFilmList(
                chipsList = it,
                filmsList = _filmList.value,
                searchQuery = searchQueryString.value
            )
        }
        addSource(_filmList) {
            value = mergeFilteredFilmList(
                chipsList = chipListLiveData.value,
                filmsList = it,
                searchQuery = searchQueryString.value
            )
        }
        addSource(searchQueryString) {
            value = mergeFilteredFilmList(
                chipsList = chipListLiveData.value,
                filmsList = _filmList.value,
                searchQuery = it
            )
        }
    }

    val filteredFilmList: LiveData<List<FilmItem>> =
        mFilteredFilmList

    private fun mergeFilteredFilmList(
        chipsList: List<ChipItem>?, filmsList: List<FilmItem>?, searchQuery: String?
    ): MutableList<FilmItem> {
        val listOfSelectedChips = chipsList.orEmpty().filter { it.chipItem.state }.map { it.chipItem.id }
        return filmsList.orEmpty()
            .filter {
                it.film.genre_id == null || (listOfSelectedChips.isEmpty() || listOfSelectedChips.contains(
                    it.film.genre_id
                ))
            }
            .filter { it.film.name.contains(searchQuery.orEmpty(), true) }
            .toMutableList()
    }

    fun setupFilmsAfterRefresh() {
        viewModelScope.launch {
            FilmRepository.fetchFilmsAfterRefresh()
        }
    }

    private fun setupChipList() {
        viewModelScope.launch {
            FilmRepository.fetchChips()
        }
    }

    private fun setupFilmList() {
        viewModelScope.launch {
            FilmRepository.fetchFilms()
        }
    }

    fun toggleChipsState(item: ChipItem) {
        FilmRepository.toggleChipsState(item)
    }
}