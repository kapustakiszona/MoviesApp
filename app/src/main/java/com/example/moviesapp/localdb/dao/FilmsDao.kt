package com.example.moviesapp.localdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moviesapp.localdb.entities.FilmEntity

@Dao
interface FilmsDao {

    @Query("SELECT * FROM films WHERE id = :id")
    suspend fun findById(id: Int): FilmEntity

    @Query("SELECT * FROM films")
    suspend fun getAllPopular(): List<FilmEntity>

    @Insert(entity = FilmEntity::class)
    suspend fun insertAllPopular(films: List<FilmEntity>)

    @Query("DELETE FROM films")
    suspend fun deleteAllPopular()
}