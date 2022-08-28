package com.example.tmdb_alkemy.model

import com.squareup.moshi.Json

data class MovieList(
    val page: Int,
    val results: List<MovieListItem>,
    @Json(name = "total_results") val totalResults: Int,
    @Json(name = "total_pages") val totalPages: Int,
)
