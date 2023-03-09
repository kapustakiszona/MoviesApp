package com.example.moviesapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesapp.Util.getJsonDataFromAsset
import com.example.moviesapp.databinding.FilmFragmentBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class FilmFragment : Fragment(), AdapterListener {

    private val filmViewModel by viewModels<FilmsViewModel>()
    private lateinit var binding: FilmFragmentBinding
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var adapterFilm: FilmAdapter
    private lateinit var films: ArrayList<Film>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FilmFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FilmFragmentBinding.bind(view)
        toolbar = binding.toolbar

        filmViewModel.chipList.observe(viewLifecycleOwner) {
            val adapterChip = ChipAdapter(it, this)
            binding.chipRecyclerView.adapter = adapterChip
        }
        filmViewModel.filmList.observe(viewLifecycleOwner) {
            setAdapter(it)
        }
        films = getListFromJson()
        searchQuery()
    }

    private fun setAdapter(list: ArrayList<Film>) {
        adapterFilm = FilmAdapter(list, this)
        binding.recyclerView.adapter = adapterFilm
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun getListFromJson(): ArrayList<Film> {
        val jsonFileString = getJsonDataFromAsset(requireContext(), "json_data.json")
        val gson = Gson()
        val filmListType = object : TypeToken<ArrayList<Film>>() {}.type
        val films: ArrayList<Film> = gson.fromJson(jsonFileString, filmListType)
        setAdapter(films)
        return films
    }

    private fun searchQuery() {
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                search(s.toString())
                binding.searchView.clearFocus()
            }

        })
    }

    private fun chipSorting(list: List<Chip>) {
        val genreList: ArrayList<String> = ArrayList()
        for (item in list) {
            if (item.state) {
                genreList.add(item.name)
            }
        }
        chipsFilter(genreList)
    }


    private fun chipsFilter(list: ArrayList<String>) {
        val genreSortedList: ArrayList<Film> = arrayListOf()
        films.filterTo(genreSortedList) { it.genre in list }
        filmViewModel.setFilteredFilmList(genreSortedList)
        if (genreSortedList.isEmpty()) {
            setAdapter(films)
        } else {
            setAdapter(filmViewModel.filmList.value as ArrayList<Film>)
        }
    }

    fun search(text: String) {
        val filterList: ArrayList<Film> = ArrayList()
        for (item in films) {

            if (item.name.lowercase().contains(text.lowercase())) {
                filterList.add(item)
            }
        }
        //update recyclerview
        setAdapter(filterList)
        Log.d("FilmFragment", " search filterlist ${filterList.size}")
    }

    override fun onClick(film: Film) {
        findNavController(binding.root).navigate(
            FilmFragmentDirections.actionFilmFragmentToDetailsFragment(
                film
            )
        )
    }

    override fun onClick(myChip: Chip) {
        myChip.state = myChip.state.not()
        filmViewModel.setChipState(myChip)
        chipSorting(filmViewModel.chipList.value!!)

    }


}


