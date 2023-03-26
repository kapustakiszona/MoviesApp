package com.example.moviesapp.ui.films.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapp.models.Chip
import com.example.moviesapp.models.Film

class FilmsViewModel : ViewModel() {
init {
    Log.d("FilmFragment", "Viewmodel created")
}
    private val _chipList = MutableLiveData<List<Chip>>()
    val chipList: LiveData<List<Chip>>  = _chipList  //тут храним акктуальный список чипсов с акктуальными статусами

    private val _searchQueryString = MutableLiveData<String>(null)
    val searchQueryString: LiveData<String> = _searchQueryString


    fun setSearchQuery(query: String) {
        _searchQueryString.value = query
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("FilmFragment", "Viewmodel _cleared")
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
        val listOfSelectedChips = chipsList.orEmpty().filter { it.state }.map { it.id }
        return ArrayList(
            filmsList.orEmpty()
                .filter { listOfSelectedChips.isEmpty() || listOfSelectedChips.contains(it.genreIds[0]) }
                .filter { it.name.contains(searchQuery.orEmpty(), true) })
    }

    fun setupChipList(chipList: List<Chip>){
        _chipList.value = chipList
    }

    fun setupFilmList(filmList: ArrayList<Film>) {// Сюда кладем загруженный из файла список фильмов,
        // либо грущзим прямо в этом методе
        _filmList.value = filmList
    }

    fun toggleChipsState(chip: Chip) {// Переключаем статус чипсов
        val oldChipsList = chipList.value.orEmpty() as ArrayList
        oldChipsList.find { it == chip }?.state = !chip.state
        _chipList.value = oldChipsList
    }

}
