package com.example.moviesapp.localdb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesapp.localdb.dao.FilmsDao
import com.example.moviesapp.localdb.dao.GenreDao
import com.example.moviesapp.localdb.entities.FilmEntity
import com.example.moviesapp.localdb.entities.GenreEntity

@Database(
    entities = [
        FilmEntity::class,
        GenreEntity::class
    ],
    version = 2
)
abstract class FilmsDatabase : RoomDatabase() {

    abstract fun getFilmDao(): FilmsDao
    abstract fun getGenreDao(): GenreDao

}