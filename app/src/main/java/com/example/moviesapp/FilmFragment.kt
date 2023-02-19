package com.example.moviesapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.Util.getJsonDataFromAsset
import com.example.moviesapp.databinding.FilmFragmentBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class FilmFragment : Fragment() {

    private lateinit var binding: FilmFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FilmFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FilmFragmentBinding.bind(view)

        val jsonFileString = getJsonDataFromAsset(requireContext(), "json_data.json")
        Log.d("FilmFragment", "$jsonFileString")
        val gson = Gson()
        val filmListType = object : TypeToken<List<Film>>() {}.type

        val films: List<Film> = gson.fromJson(jsonFileString, filmListType)
        films.forEachIndexed{idx, film -> Log.d("FilmFragment", ">Item№ $idx:\\n$film")}


        val adapter = FilmAdapter(films)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        Log.d("FilmFragment", "LIST SIZE IS ${FilmDatasource().filmList.size}")

    }
}


