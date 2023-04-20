package com.example.moviesapp.localdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesapp.localdb.entities.FilmEntity

@Dao
interface FilmsDao {

    @Query("SELECT * FROM films WHERE id = :id")
    suspend fun findFilmDetailsById(id: Int): FilmEntity?

    @Query("SELECT * FROM films")
    suspend fun getAllPopularFilms(): List<FilmEntity>?

    @Insert(entity = FilmEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPopularFilms(films: List<FilmEntity>)

    @Query("UPDATE films SET genre_name = (SELECT name FROM genres WHERE id = films.genre_ids)")
    suspend fun setupGenreNameInFilms()

    @Query("DELETE FROM films")
    suspend fun deleteAllPopularFilms()

}