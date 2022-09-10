package com.example.tmdb_alkemy.model

import com.squareup.moshi.Json

data class MovieDetails(
    val id: Int = 0,
    val title: String = "",
    val genres: List<Genre> = listOf(),
    val tagline: String = "",
    @Json(name = "vote_average") val voteAverage: Double = 0.0,
    @Json(name = "release_date") val releaseDate: String = "",
    @Json(name = "overview") val description: String = "",
    @Json(name = "original_language") val originalLanguage: String = "",
    @Json(name = "poster_path") val posterPath: String = "",
)
