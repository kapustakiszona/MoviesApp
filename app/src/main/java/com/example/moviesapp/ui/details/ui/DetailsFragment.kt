package com.example.moviesapp.ui.details.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentDetailsBinding
import com.example.moviesapp.models.Film
import com.example.moviesapp.network.BASE_IMAGE_URL
import com.example.moviesapp.ui.adapter.BaseRecyclerAdapter
import com.example.moviesapp.ui.details.vm.DetailsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

const val TAG = "MY_LOG"

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsBinding
    private val adapterActor = BaseRecyclerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDetailsBinding.bind(view)
        BottomSheetBehavior.from(view.findViewById(R.id.sheet)).apply {
            peekHeight = 200
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        val filmId = args.filmId
        initVM(filmId = filmId)
        initUI(filmId = filmId)
    }

    private fun initVM(filmId: Int) {
        viewModel.getActorsListById(filmId)
        viewModel.getFilmDetailsById(filmId)
        viewModel.actors.observe(viewLifecycleOwner, Observer {
            adapterActor.updateWithDiffUtils(it)
            adapterActor.notifyDataSetChanged()
        })
        viewModel.film.observe(viewLifecycleOwner, Observer {
            initActorItem(it)
        })
    }

    private fun initActorItem(film: Film) {
        with(binding) {
            ratingBar?.rating = film.rating.div(2)
            detailPosterIv.load(film.getImageUrl(BASE_IMAGE_URL))
            detailNameTv.text = film.name
            detailDescriptionTv.text = film.description
            detailDateTv.text = film.date_publication
            genreChip?.text = film.genreName
            ageDetailRatingTv?.text = film.adult
        }
    }

    private fun initUI(filmId: Int) {
        with(binding) {
            actorsRecyclerView?.adapter = adapterActor
        }
    }
}