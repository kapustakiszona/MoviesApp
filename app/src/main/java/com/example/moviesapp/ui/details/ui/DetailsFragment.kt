package com.example.moviesapp.ui.details.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.moviesapp.databinding.FragmentDetailsBinding
import com.example.moviesapp.models.Actor
import com.example.moviesapp.models.Film
import com.example.moviesapp.network.Converter
import com.example.moviesapp.network.NetworkClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var actorListAdapter: ActorAdapter
    private lateinit var film: Film
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDetailsBinding.bind(view)
        val filmId = args.filmId
        initUI(filmId)
    }

    private fun initUI(filmId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = withContext(Dispatchers.IO) {
                // Выполнить какую-то операцию в фоновом потоке
                NetworkClient.create().getMovieDetailsById(filmId)

            }
            film = Converter().convertResponseToFilmDetails(result)
            with(binding) {
                ratingBar?.rating = film.rating.div(2)
                detailPosterIv.load("https://image.tmdb.org/t/p/original/" + film.photo){
                    crossfade(1000)
                }
                Log.d("FilmFragment", "image is: ${detailPosterIv.drawable}")
                Log.d("FilmFragment", "api image is: ${film.photo}")
                detailNameTv.text = film.name
                detailDescriptionTv.text = film.description
                detailDateTv.text = film.date_publication
                genreChip?.text = film.genreName
                ageDetailRatingTv?.text = film.adult

            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            val result = withContext(Dispatchers.IO) {
                NetworkClient.create().getActorsListById(filmId)
            }
            val actorList = Converter().convertResponseToActorsList(result.cast)
            setActorsListAdapter(actorList)
        }
    }

    private fun setActorsListAdapter(list: ArrayList<Actor>) {
        actorListAdapter = ActorAdapter(list)
        binding.actorsRecyclerView?.adapter = actorListAdapter

    }

}