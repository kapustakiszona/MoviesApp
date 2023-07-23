package com.example.moviesapp.localdb.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviesapp.MoviesApp
import com.example.moviesapp.localdb.Converters
import com.example.moviesapp.localdb.dao.FilmsDao
import com.example.moviesapp.localdb.dao.GenreDao
import com.example.moviesapp.localdb.entities.FilmEntity
import com.example.moviesapp.localdb.entities.GenreEntity

@TypeConverters(Converters::class)
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

    companion object {
        @Volatile
        private var INSTANCE: FilmsDatabase? = null
        fun getDatabase(): FilmsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    MoviesApp.instance,
                    FilmsDatabase::class.java,
                    "movies_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}