package com.example.moviesapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesapp.R
import com.example.moviesapp.network.NetworkClient

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}