package com.example.moviesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.moviesapp.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDetailsBinding.bind(view)

        with(binding) {
            detailRatingTv.text = args.film.rating.toString()
            detailPosterIv.setImageResource(args.film.photo)
            detailNameTv.text = args.film.name
            detailDescriptionTv.text = args.film.description
            detailDateTv.text = args.film.date_publication
        }
    }
}