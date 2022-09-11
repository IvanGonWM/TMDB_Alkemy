package com.example.tmdb_alkemy.ui.main_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb_alkemy.model.MovieListItem
import com.example.tmdb_alkemy.network.FacadeRepository
import kotlinx.coroutines.launch

enum class TmdbApiStatus { LOADING, ERROR, DONE }

class MainListViewModel : ViewModel()  {

    private val _status = MutableLiveData<TmdbApiStatus>()
    val status: LiveData<TmdbApiStatus> = _status

    private val _movieList = MutableLiveData<List<MovieListItem>>()
    val movieList: LiveData<List<MovieListItem>> = _movieList

    var isLastPage: Boolean = false
    var isSearching: Boolean = false

    init {
        getPopularMovies()
    }


    private fun getPopularMovies() {
        viewModelScope.launch {
            _status.value = TmdbApiStatus.LOADING
            try {
                _movieList.value = FacadeRepository.getLoadedMoviesList()
                _status.value = TmdbApiStatus.DONE
            } catch (e: Exception) {
                _status.value = TmdbApiStatus.ERROR
            }
        }
    }

    fun getNextPopularMovies(): Boolean {return true}


    fun refreshMovies() {
        viewModelScope.launch() {
        FacadeRepository.refreshMovies()
        _movieList.value = FacadeRepository.getLoadedMoviesList()
    }}
}