package com.example.moviesapp.network

import com.example.moviesapp.network.models.ActorsListResponse
import com.example.moviesapp.network.models.DetailResponse
import com.example.moviesapp.network.models.GenreListResponse
import com.example.moviesapp.network.models.ResponsePopularMovies
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(): ResponsePopularMovies

    @GET("movie/{movie_id}")
    suspend fun getMovieDetailsById(@Path("movie_id") id: Int): DetailResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getActorsListById(@Path("movie_id") id: Int): ActorsListResponse

    @GET("genre/movie/list")
    suspend fun getGenreList(): GenreListResponse
}