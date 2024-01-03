package com.example.moviesapp.data.localdb.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.moviesapp.models.Film

@Entity(
    tableName = "films",
    indices = [
        Index("id", unique = true)
    ]
)
data class FilmEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val datePublication: String,
    val adult: String,
    val rating: Float,
    val description: String,
    val photo: String,
    val genreIds: Int?,
    var genreName: String
) {
    fun toFilm(): Film = Film(
        id = id,
        name = name,
        date_publication = datePublication,
        adult = adult,
        rating = rating,
        description = description,
        photo = photo,
        genre_id = genreIds ?: 0,
        genre_name = genreName
    )
}
