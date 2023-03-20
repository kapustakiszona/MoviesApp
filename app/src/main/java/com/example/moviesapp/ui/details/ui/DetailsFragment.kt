package com.example.moviesapp.ui.details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.moviesapp.databinding.FragmentDetailsBinding
import com.example.moviesapp.models.Actor


class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var actorListAdapter: ActorAdapter
    private lateinit var actorsList: ArrayList<Actor>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDetailsBinding.bind(view)
        actorsList = args.film.getActorsList()
        setActorsListAdapter(actorsList)


        with(binding) {
            ratingBar?.rating = args.film.rating.div(2)
            detailPosterIv.setImageResource(args.film.photo)
            detailNameTv.text = args.film.name
            detailDescriptionTv.text = args.film.description
            detailDateTv.text = args.film.date_publication
            genreChip?.text = args.film.genre
            ageDetailRatingTv?.text = args.film.rating.toString()

        }
    }


    private fun setActorsListAdapter(list: ArrayList<Actor>) {
        actorListAdapter = ActorAdapter(list)
        binding.actorsRecyclerView?.adapter = actorListAdapter

    }

}