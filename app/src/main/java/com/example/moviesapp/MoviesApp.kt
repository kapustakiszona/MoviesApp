package com.example.moviesapp

import android.app.Application
import com.example.moviesapp.network.NetworkClient

class MoviesApp : Application() {
    companion object {
        lateinit var instance: MoviesApp
    }

    override fun onCreate() {
        super.onCreate()
        NetworkClient.initNetwork()
        instance = this
    }
}