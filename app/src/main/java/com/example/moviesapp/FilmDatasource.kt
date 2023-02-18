package com.example.moviesapp

import net.datafaker.Faker

class FilmDatasource {
    var filmList = listOf<Film>()


    init {
        val faker = Faker()
        filmList = (1..30).map {
            Film(
                id = it.toLong(),
                name = faker.book().title(),
                date_publication = faker.number().numberBetween(1990, 2022).toString(),
                rating = faker.number().numberBetween(1, 10),
                description = faker.lorem().sentence(200),
                photo = R.drawable.the_shining
            )
        }.toList()
    }


}

