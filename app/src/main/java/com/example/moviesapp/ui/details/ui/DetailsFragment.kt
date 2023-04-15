package com.example.moviesapp.ui.details.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentDetailsBinding
import com.example.moviesapp.models.Film
import com.example.moviesapp.network.repository.FilmRepository
import com.example.moviesapp.ui.adapter.BaseRecyclerAdapter
import com.example.moviesapp.ui.details.vm.DetailsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

const val TAG = "MY_LOG"

class DetailsFragment : Fragment() {
    private val viewModel by viewModels<DetailsViewModel>()
    private val args: DetailsFragmentArgs by navArgs()
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val adapterActor = BaseRecyclerAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initVM(filmId = args.filmId)
        initUI()
    }

    private fun initVM(filmId: Int) {
        viewModel.onInitialized(filmId)
        viewModel.actorLiveData.observe(viewLifecycleOwner) {
            adapterActor.updateWithDiffUtils(it.orEmpty())
        }
        viewModel.actorError.observe(viewLifecycleOwner) {
            binding.detailDescriptionTv.append(it)
        }
        viewModel.filmError.observe(viewLifecycleOwner) {
            binding.detailDescriptionTv.append(it)
        }
        viewModel.filmLiveData.observe(viewLifecycleOwner) {
            Log.d(TAG, "film: ${it?.name}")
            initActorItem(it)
        }
    }

    private fun initActorItem(film: Film?) {
        with(binding) {
            if (film != null) {
                ratingBar.rating = film.rating.div(2)
                detailPosterIv.load(film.getImageUrl())
                detailNameTv.text = film.name
                detailDescriptionTv.text = film.description
                detailDateTv.text = film.date_publication
                genreChip.text = film.genre_name
                ageDetailRatingTv.text = film.adult
            }
        }
    }

    override fun onDestroy() {
        FilmRepository.clearLiveData()
        super.onDestroy()
    }

    private fun initUI() {
        with(binding) {
            actorsRecyclerView.adapter = adapterActor
        }
        view?.let {
            BottomSheetBehavior.from(it.findViewById(R.id.sheet)).apply {
                peekHeight = 200
                this.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

}