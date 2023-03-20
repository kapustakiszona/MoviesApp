package com.example.moviesapp.network

import retrofit2.http.GET

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(): ResponsePopularMovies

    @GET("movie/568")
    suspend fun getMovieById(): Result
}