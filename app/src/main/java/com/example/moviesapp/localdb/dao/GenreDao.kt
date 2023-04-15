package com.example.moviesapp.localdb.dao

import androidx.room.*
import com.example.moviesapp.localdb.entities.GenreEntity
import com.example.moviesapp.models.Chip

@Dao
interface GenreDao {

    @Query("SELECT * FROM genres")
    suspend fun getAllGenres(): List<GenreEntity>

    @Query("SELECT * FROM genres WHERE id = :id")
    suspend fun getById(id: Int): GenreEntity

    @Insert(entity = GenreEntity::class)
    suspend fun insertAllGenres(genres: List<Chip>)

    @Query("DELETE FROM genres")
    suspend fun deleteAllGenres()
}