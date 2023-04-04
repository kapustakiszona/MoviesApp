package com.example.moviesapp

import android.app.Application
import com.example.moviesapp.network.NetworkClient

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        NetworkClient.initNetwork()
    }
}