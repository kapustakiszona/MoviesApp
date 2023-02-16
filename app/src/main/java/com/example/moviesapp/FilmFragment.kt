package com.example.moviesapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.databinding.FilmFragmentBinding

class FilmFragment : Fragment() {

    private lateinit var binding: FilmFragmentBinding
    private val filmDatasource = FilmDatasource()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FilmFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FilmFragmentBinding.bind(view)

        val adapter = FilmAdapter(filmDatasource.filmList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        Log.d("FilmFragment", "LIST SIZE IS ${FilmDatasource().filmList.size}")

    }

}

