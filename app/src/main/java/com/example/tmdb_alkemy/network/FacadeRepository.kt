package com.example.tmdb_alkemy.network

import com.example.tmdb_alkemy.model.MovieDetails
import com.example.tmdb_alkemy.model.MovieListItem

object FacadeRepository {

    private val loadedMovieList: MutableList<MovieListItem> = mutableListOf()
    private val openedMovieList: MutableList<MovieDetails> = mutableListOf()

    private var loading: Boolean = false
    var currentPage: Int = 1
    var totalPages: Int = 1
    private var totalMovies: Int = 0

    suspend fun getNextPage() {

        if ((currentPage > totalPages) || loading) return

        loading = true
        try {
            val listResult = MoviesDatabaseApi.retrofitService.getPagedMovieList(currentPage)
            totalPages = listResult.totalPages
            totalMovies = listResult.totalResults
            loadedMovieList.addAll(listResult.results)
            currentPage++
            loading = false
        } catch (e: Exception) {
            loading = false
            throw e
        }
    }

    fun getLoadedMoviesList(): List<MovieListItem> = loadedMovieList

    suspend fun getOpenedMovieDetails(id: Int): MovieDetails {
        val alreadyExists = openedMovieList.firstOrNull { movie -> movie.id == id }
        if (alreadyExists != null) return alreadyExists

        val searchedMovieDetails = MoviesDatabaseApi.retrofitService.getMovieDetails(id = id)
        openedMovieList.add(searchedMovieDetails)
        return searchedMovieDetails
    }

    suspend fun refreshMovies() {
        loadedMovieList.clear()
        openedMovieList.clear()
        totalMovies = 0
        totalPages = 1
        currentPage = 1
        getNextPage()
    }
}