package com.example.moviesapp.localdb.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.moviesapp.models.Chip
import com.example.moviesapp.ui.films.ui.ChipItem

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
    fun toChipItem(): ChipItem = ChipItem(
        Chip(
            id = id,
            name = name
        )
    )
}