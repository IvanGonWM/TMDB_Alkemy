package com.example.tmdb_alkemy.model

import com.squareup.moshi.Json

data class MovieDetails(
    val id: Int,
    val title: String,
    val genres: List<Genre>,
    val tagline: String,
    @Json(name = "overview") val description: String,
    @Json(name = "original_language") val originalLanguage: String,
    @Json(name = "poster_path") val posterPath: String,
)
