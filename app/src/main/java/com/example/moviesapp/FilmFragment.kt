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
    private lateinit var adapterChip: ChipAdapter


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
        getListFromJson()
        filmViewModel.filteredFilmList.observe(viewLifecycleOwner) {
            setAdapter(filmViewModel.showFilmList(it))
        }

        filmViewModel.chipList.observe(viewLifecycleOwner) {
            adapterChip = ChipAdapter(it, this)
            binding.chipRecyclerView.adapter = adapterChip
        }
        searchQuery()
    }

    private fun setAdapter(list: ArrayList<Film>) {
        adapterFilm = FilmAdapter(list, this)
        binding.recyclerView.adapter = adapterFilm
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun getListFromJson() {
        val jsonFileString = getJsonDataFromAsset(requireContext(), "json_data.json")
        val gson = Gson()
        val filmListType = object : TypeToken<ArrayList<Film>>() {}.type
        val films: ArrayList<Film> = gson.fromJson(jsonFileString, filmListType)
        Log.d("FilmFragment", " Film list size in init fun ${films.size}")
        setAdapter(films)
        filmViewModel.setupFilmList(films)
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

    fun search(text: String) {
        val filterList: ArrayList<Film> = ArrayList()
        for (item in filmViewModel.filteredFilmList.value!!) {

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
        filmViewModel.toggleChipsState(myChip)
    }


}


