package com.example.moviesapp.ui.films.vm

import androidx.lifecycle.*
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
        mFilteredFilmList// Лайвдата которая торчит наружу

    private fun mergeFilteredFilmList(
        chipsList: List<ChipItem>?, filmsList: List<FilmItem>?, searchQuery: String?
    ): MutableList<FilmItem> {
        val listOfSelectedChips =
            chipsList.orEmpty().filter { it.chipItem.state }.map { it.chipItem.id }
        return filmsList.orEmpty()
            .filter {
                it.film.genre_ids.isNotEmpty() && (listOfSelectedChips.isEmpty() || listOfSelectedChips.contains(
                    it.film.genre_ids[0]
                ))
            }
            .filter { it.film.name.contains(searchQuery.orEmpty(), true) }
            .toMutableList()
    }


    fun setupChipList() {
        viewModelScope.launch {
            FilmRepository.fetchChips()
        }
    }


    fun setupFilmList() {
        viewModelScope.launch {
            FilmRepository.fetchFilms()
        }
    }

    fun toggleChipsState(item: ChipItem) {// Переключаем статус чипсов
        FilmRepository.toggleChipsState(item)
    }
}