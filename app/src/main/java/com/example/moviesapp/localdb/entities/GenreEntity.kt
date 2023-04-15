package com.example.moviesapp.localdb.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.moviesapp.network.models.GenreListResponse

@Entity(
    tableName = "genres",
    indices = [
        Index("name", unique = true)
    ]
)
data class GenreEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val state: Boolean
) {
    fun toGenre(): GenreListResponse.Genre = GenreListResponse.Genre(
        id = id,
        name = name
    )
}