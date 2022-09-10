package com.example.tmdb_alkemy.network

import com.example.tmdb_alkemy.model.MovieListItem

object FacadeRepository {

    private val loadedMovieList: MutableList<MovieListItem> = mutableListOf()

    private var currentPage: Int = 1
    private var totalPages: Int = 1
    private var totalMovies:  Int = 0
    private var loading: Boolean = false

    suspend fun getNextPage() {
        if ((currentPage > totalPages) || loading)
            return
        loading = true
        try {
            val listResult = MoviesDatabaseApi.retrofitService.getPopularMovies(currentPage)
            currentPage++
            loadedMovieList.addAll(listResult.results)
            totalPages = listResult.totalPages
            totalMovies = listResult.totalResults
            loading = false
        } catch (e: Exception) {
            loading = false
        }

    }
}