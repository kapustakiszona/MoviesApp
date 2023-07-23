package com.example.moviesapp.network

import com.example.moviesapp.network.models.*
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmsService {

    @GET("movie/popular")
    suspend fun getPopularMovies(): ResponsePopularFilms

    @GET("movie/{movie_id}")
    suspend fun getMovieDetailsById(@Path("movie_id") id: Int): FilmDetailsResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getActorsListById(@Path("movie_id") id: Int): ActorsListResponse

    @GET("genre/movie/list")
    suspend fun getGenreList(): GenreListResponse
}