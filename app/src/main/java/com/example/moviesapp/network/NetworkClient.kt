package com.example.moviesapp.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_REQUEST_URL = "https://api.themoviedb.org/3/"
const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/original/"

object NetworkClient {

    lateinit var filmsService: FilmsService
        private set

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter("api_key", "e9a92b2fe9c5e6707013ce401ef38915")
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    fun initNetwork() {
        filmsService = retrofitBuilder().create(FilmsService::class.java)
    }

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    private fun retrofitBuilder() =
        Retrofit.Builder().client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_REQUEST_URL)
            .build()

}