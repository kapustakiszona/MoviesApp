package com.example.moviesapp.ui.films.ui


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesapp.Util.getJsonDataFromAsset
import com.example.moviesapp.databinding.FilmFragmentBinding
import com.example.moviesapp.models.Chip
import com.example.moviesapp.models.Film
import com.example.moviesapp.network.NetworkClient
import com.example.moviesapp.ui.films.vm.FilmsViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FilmFragment : Fragment(), AdapterListener {

    private val filmViewModel by viewModels<FilmsViewModel>()
    private lateinit var binding: FilmFragmentBinding

    private val adapterFilm = FilmAdapter(emptyList(), this)
    private val adapterChip = ChipAdapter(emptyList(), this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FilmFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initVM()
        getListFromJson()
        CoroutineScope(Dispatchers.Main).launch {
            NetworkClient.create().getMovieById()
        }
    }

    private fun initUi() {
        with(binding) {
            recyclerView.apply {
                adapter = adapterFilm
                layoutManager = GridLayoutManager(requireContext(), 2)
            }
            chipRecyclerView.apply {
                adapter = adapterChip
            }
            searchView.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    filmViewModel.setSearchQuery(s.toString())
                    searchView.clearFocus()
                }
            })
        }
    }

    private fun initVM() {
        filmViewModel.filteredFilmList.observe(viewLifecycleOwner) {
            adapterFilm.filmList = it
            showEmptyListPlaceholder(it)
            Log.d("FilmFragment", " vm film list size ${it.size}")
            adapterFilm.notifyDataSetChanged()
        }
        filmViewModel.chipList.observe(viewLifecycleOwner) {
            adapterChip.chipsList = it
            adapterChip.notifyDataSetChanged()
        }
    }

    private fun showEmptyListPlaceholder(filmList: ArrayList<Film>) {
        binding.placeholderTv.isGone = filmList.isNotEmpty()
    }

    private fun getListFromJson() {
        val jsonFileString = getJsonDataFromAsset(requireContext(), "json_data.json")
        val gson = Gson()
        val filmListType = object : TypeToken<ArrayList<Film>>() {}.type
        val films: ArrayList<Film> = gson.fromJson(jsonFileString, filmListType)
        filmViewModel.setupFilmList(films)
    }


    override fun onFilmSelected(film: Film) {
        findNavController(binding.root).navigate(
            FilmFragmentDirections.actionFilmFragmentToDetailsFragment(
                film
            )
        )
    }

    override fun onChipSelected(myChip: Chip) {
        filmViewModel.toggleChipsState(myChip)
    }


}

