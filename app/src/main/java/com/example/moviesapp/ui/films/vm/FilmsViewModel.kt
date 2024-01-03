package com.example.moviesapp.ui.films.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.models.Chip
import com.example.moviesapp.models.Film
import com.example.moviesapp.repository.FilmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmsViewModel @Inject constructor(
    private val filmRepository: FilmRepository
) : ViewModel() {
    init {
        setupChipList()
        setupFilmList()
    }

    val chipError = filmRepository.chipListError
    val filmError = filmRepository.filmListError
    val chipList = filmRepository.chipList

    private val _searchQueryString = MutableLiveData<String>(null)
    private val _filteredFilmList = MediatorLiveData<List<Film>>().apply {
        addSource(chipList) {
            value = mergeFilteredFilmList(
                chipsList = it,
                filmsList = filmRepository.filmList.value,
                searchQuery = _searchQueryString.value
            )
        }
        addSource(filmRepository.filmList) {
            value = mergeFilteredFilmList(
                chipsList = chipList.value,
                filmsList = it,
                searchQuery = _searchQueryString.value
            )
        }
        addSource(_searchQueryString) {
            value = mergeFilteredFilmList(
                chipsList = chipList.value,
                filmsList = filmRepository.filmList.value,
                searchQuery = it
            )
        }
    }

    val filteredFilmList: LiveData<List<Film>> =
        _filteredFilmList

    private fun mergeFilteredFilmList(
        chipsList: List<Chip>?, filmsList: List<Film>?, searchQuery: String?
    ): MutableList<Film> {
        val listOfSelectedChips = chipsList.orEmpty().filter { it.state }.map { it.id }
        return filmsList.orEmpty()
            .filter {
                it.genre_id == null || (listOfSelectedChips.isEmpty() || listOfSelectedChips.contains(
                    it.genre_id
                ))
            }
            .filter { it.name.contains(searchQuery.orEmpty(), true) }
            .toMutableList()
    }

    fun setupFilmsAfterRefresh() {
        viewModelScope.launch {
            filmRepository.fetchFilmsAfterRefresh()
        }
    }

    fun setSearchQuery(query: String) {
        _searchQueryString.value = query
    }

    private fun setupChipList() {
        viewModelScope.launch {
            filmRepository.fetchChips()
        }
    }

    private fun setupFilmList() {
        viewModelScope.launch {
            filmRepository.fetchFilms()
        }
    }

    fun toggleChipsState(item: Chip) {
        val oldChipsList = chipList.value.orEmpty()
        oldChipsList.find { it == item }?.state = !item.state
        filmRepository.setCHipList(oldChipsList)
    }
}