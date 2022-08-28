package com.example.tmdb_alkemy.model

import com.squareup.moshi.Json

data class MovieListItem(
    val id: Int,
    val title: String,
    @Json(name = "overview") val description: String,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "poster_path") val posterPath: String,
)
