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
import com.example.moviesapp.databinding.FilmFragmentBinding
import com.example.moviesapp.ui.adapter.BaseListItem
import com.example.moviesapp.ui.adapter.BaseRecyclerAdapter
import com.example.moviesapp.ui.details.ui.TAG
import com.example.moviesapp.ui.films.vm.FilmsViewModel


class FilmFragment : Fragment() {

    private val filmViewModel by viewModels<FilmsViewModel>()
    private lateinit var binding: FilmFragmentBinding

    private val adapterFilm = BaseRecyclerAdapter { item, _ ->
        onFilmItemSelected(item)
    }
    private val adapterChip = BaseRecyclerAdapter { item, _ ->
        Log.d(TAG, "Item in adapter : ${item.getViewId()}")
        onChipItemSelected(item)
    }

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
        filmViewModel.setupFilmList()
        filmViewModel.filteredFilmList.observe(viewLifecycleOwner) {
            adapterFilm.updateWithDiffUtils(it)
            showEmptyListPlaceholder(it)
            adapterFilm.notifyDataSetChanged()
            Log.d(TAG, "observer filmlist: ${it.size}")
        }
        filmViewModel.chipList.observe(viewLifecycleOwner) {
            adapterChip.updateWithDiffUtils(it)
            adapterChip.notifyDataSetChanged()
            Log.d(TAG, "observer chiplist: ${it.size}")
        }
    }

    private fun showEmptyListPlaceholder(filmList: List<FilmItem>) {
        binding.placeholderTv.isGone = filmList.isNotEmpty()
    }

    private fun onFilmItemSelected(item: BaseListItem) {
        if (item is FilmItem) {
            findNavController(binding.root).navigate(
                FilmFragmentDirections.actionFilmFragmentToDetailsFragment(
                    item.film.id
                )
            )
        }
    }

    private fun onChipItemSelected(item: BaseListItem) {
        Log.d(TAG, "OnchipItem selected")
        if (item is ChipItem)
            filmViewModel.toggleChipsState(item)
    }


}


