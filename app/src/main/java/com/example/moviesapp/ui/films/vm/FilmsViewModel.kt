package com.example.moviesapp.ui.films.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



import com.example.moviesapp.models.Chip
import com.example.moviesapp.models.Film

class FilmsViewModel : ViewModel() {

    private val chipNameResource = listOf(
        "боевик",
        "вестерн",
        "военный",
        "детектив",
        "документальный",
        "драма",
        "история",
        "комедия",
        "криминал",
        "мелодрама",
        "музыка",
        "мультфильм",
        "приключения",
        "семейный",
        "телевизионный фильм",
        "триллер",
        "ужасы",
        "фантастика",
        "фэнтези"
    )
    private val _chipList = MutableLiveData(chipNameResource.map {
        Chip(
            name = it,
        )
    })
    val chipList: LiveData<List<Chip>>  = _chipList  //тут храним акктуальный список чипсов с акктуальными статусами

    private val _searchQueryString = MutableLiveData<String>(null)
    val searchQueryString: LiveData<String> = _searchQueryString
    fun setSearchQuery(query: String) {
        _searchQueryString.value = query
    }

    private val _filmList = MutableLiveData<ArrayList<Film>>()

    private val mFilteredFilmList = MediatorLiveData<ArrayList<Film>>().apply {
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

    val filteredFilmList: LiveData<java.util.ArrayList<Film>> =
        mFilteredFilmList// Лайвдата которая торчит наружу

    private fun mergeFilteredFilmList(
        chipsList: List<Chip>?,
        filmsList: ArrayList<Film>?,
        searchQuery: String?
    ): ArrayList<Film> {
        val listOfSelectedChips = chipsList.orEmpty().filter { it.state }.map { it.name }
        return ArrayList(
            filmsList.orEmpty()
                .filter { listOfSelectedChips.isEmpty() || listOfSelectedChips.contains(it.genre) }
                .filter { it.name.contains(searchQuery.orEmpty(), true) })
    }

    fun setupFilmList(filmList: ArrayList<Film>) {// Сюда кладем загруженный из файла список фильмов,
        // либо грущзим прямо в этом методе
        _filmList.value = filmList
    }

    fun toggleChipsState(chip: Chip) {// Переключаем статус чипсов
        val oldChipsList = chipList.value.orEmpty().toMutableList()
        oldChipsList.find { it == chip }?.state = !chip.state
        _chipList.value = oldChipsList
    }

}
