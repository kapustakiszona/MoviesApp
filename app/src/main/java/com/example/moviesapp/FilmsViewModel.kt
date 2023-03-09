package com.example.moviesapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
    private val _chipList: MutableLiveData<List<Chip>> = MutableLiveData()
    val chipList: LiveData<List<Chip>> get() = _chipList

    private val _filmList: MutableLiveData<ArrayList<Film>> = MutableLiveData()
    val filmList: LiveData<ArrayList<Film>> get() = _filmList

    fun setFilteredFilmList(filteredFilmList: ArrayList<Film>) {
        _filmList.value = filteredFilmList
    }

    init {
        _chipList.value = (0..18).map {
            Chip(
                name = chipNameResource[it],
            )
        }
    }

    fun setChipState(chip: Chip) {
        chipList.value?.map {
            if (it.name == chip.name) {
                it.state.not()
            }
        }
    }


}
