package com.example.moviesapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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
    private val _chipList = MutableLiveData(chipNameResource.map {
        Chip(
            name = it,
        )
    })
    val chipList: LiveData<List<Chip>> get() = _chipList  //тут храним акктуальный список чипсов с акктуальными статусами

    private val _filmList = MutableLiveData<ArrayList<Film>>()

    private val mFilteredFilmList = MediatorLiveData<ArrayList<Film>>().apply {
        addSource(chipList) {
            value = mergeFilteredFilmList(chipsList = it, filmsList = _filmList.value)
        }
        addSource(_filmList) {
            value = mergeFilteredFilmList(chipsList = chipList.value, filmsList = it)
        }
    }

    fun showFilmList(filteredFilmList: ArrayList<Film>): ArrayList<Film> {
        return if (filteredFilmList.isEmpty())
            _filmList.value!!
        else
            filteredFilmList
    }

    val filteredFilmList: LiveData<java.util.ArrayList<Film>> =
        mFilteredFilmList// Лайвдата которая торчит наружу

    private fun mergeFilteredFilmList(
        chipsList: List<Chip>?,
        filmsList: ArrayList<Film>?
    ): ArrayList<Film> {
        val genreList: ArrayList<String> = ArrayList()
        for (item in chipsList!!) {
            if (item.state) {
                genreList.add(item.name)
            }
        }
        val filteredFilmList: ArrayList<Film> = arrayListOf()
        filmsList?.filterTo(filteredFilmList) { it.genre in genreList }
        return filteredFilmList
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
