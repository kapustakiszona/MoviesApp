package com.example.moviesapp

import android.app.Application
import com.example.moviesapp.network.NetworkClient
import timber.log.Timber


class MoviesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        NetworkClient.initNetwork()
    }
}