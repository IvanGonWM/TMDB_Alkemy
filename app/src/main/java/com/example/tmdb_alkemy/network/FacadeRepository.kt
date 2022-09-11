package com.example.tmdb_alkemy.network

import com.example.tmdb_alkemy.model.MovieDetails
import com.example.tmdb_alkemy.model.MovieListItem

object FacadeRepository {

    private val loadedMovieList: MutableList<MovieListItem> = mutableListOf()
    private val openedMovieList: MutableList<MovieDetails> = mutableListOf()

    private var currentPage: Int = 1
    private var totalPages: Int = 1
    private var totalMovies:  Int = 0
    private var loading: Boolean = false

    suspend fun getNextPage() {
        if ((currentPage > totalPages) || loading)
            return
        loading = true
        try {
            val listResult = MoviesDatabaseApi.retrofitService.getPagedMovieList(currentPage)
            currentPage++
            loadedMovieList.addAll(listResult.results)
            totalPages = listResult.totalPages
            totalMovies = listResult.totalResults
            loading = false
        } catch (e: Exception) {
            loading = false
        }
    }

    fun getLoadedMoviesList(): List<MovieListItem> {
        return loadedMovieList
    }

    suspend fun getOpenedMovieDetails(id: Int): MovieDetails {
        val alreadyExists = openedMovieList.firstOrNull{ movie -> movie.id == id }
        if (alreadyExists != null) return alreadyExists

        val searchedMovieDetails = MoviesDatabaseApi.retrofitService.getMovieDetails(id = id)
        openedMovieList.add(searchedMovieDetails)
        return searchedMovieDetails
    }

    suspend fun refreshMovies() {
        loadedMovieList.clear()
        openedMovieList.clear()
        currentPage = 1
        totalPages = 1
        totalMovies = 0
        getNextPage()
    }
}