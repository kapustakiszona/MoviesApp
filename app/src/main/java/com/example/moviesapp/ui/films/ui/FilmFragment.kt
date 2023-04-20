package com.example.moviesapp.ui.films.ui


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesapp.databinding.FilmFragmentBinding
import com.example.moviesapp.models.Film
import com.example.moviesapp.ui.adapter.BaseListItem
import com.example.moviesapp.ui.adapter.BaseRecyclerAdapter
import com.example.moviesapp.ui.films.vm.FilmsViewModel


class FilmFragment : Fragment() {

    private val filmViewModel by viewModels<FilmsViewModel>()
    private lateinit var binding: FilmFragmentBinding

    private val adapterFilm = BaseRecyclerAdapter { item, _ ->
        onFilmItemSelected(item)
    }
    private val adapterChip = BaseRecyclerAdapter { item, _ ->
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
        swipeToRefresh()
    }

    private fun swipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            filmViewModel.setupFilmsAfterRefresh()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initUi() {
        with(binding) {
            popularRecyclerView.apply {
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
        filmViewModel.filteredFilmList.observe(viewLifecycleOwner) { it ->
            adapterFilm.updateWithDiffUtils(it.map { FilmItem(it) })
            showEmptyListPlaceholder(it)
        }
        filmViewModel.chipListLiveData.observe(viewLifecycleOwner) { it ->
            adapterChip.updateWithDiffUtils(it.orEmpty().map { ChipItem(it) })
        }
        filmViewModel.chipError.observe(viewLifecycleOwner) {
            showErrorInfo(it)
        }
        filmViewModel.filmError.observe(viewLifecycleOwner) {
            showErrorInfo(it)
        }
    }

    private fun showErrorInfo(error: String?) {
        binding.placeholderTv.append(error)
    }

    private fun showEmptyListPlaceholder(filmList: List<Film>) {
        binding.placeholderTv.isGone = filmList.isNotEmpty()
    }

    private fun onFilmItemSelected(item: BaseListItem) {
        if (item is FilmItem) {
            findNavController().navigate(
                FilmFragmentDirections.actionFilmFragmentToDetailsFragment(
                    item.film.id
                )
            )
        }
    }

    private fun onChipItemSelected(item: BaseListItem) {
        if (item is ChipItem)
            filmViewModel.toggleChipsState(item.chipItem)
    }


}


