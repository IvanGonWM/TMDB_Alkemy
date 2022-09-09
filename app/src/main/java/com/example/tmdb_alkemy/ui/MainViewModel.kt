package com.example.tmdb_alkemy.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb_alkemy.model.MovieList
import com.example.tmdb_alkemy.model.MovieListItem
import com.example.tmdb_alkemy.network.MoviesDatabaseApi
import kotlinx.coroutines.launch

enum class TmdbApiStatus { LOADING, ERROR, DONE }

class MainViewModel : ViewModel()  {

    private val _status = MutableLiveData<TmdbApiStatus>()
    val status: LiveData<TmdbApiStatus> = _status

    private val _popularMovies = MutableLiveData<MovieList>()
    val popularMovies: LiveData<MovieList> = _popularMovies

    private val _selectedCard = MutableLiveData<MovieListItem>()
    val selectedCard : LiveData<MovieListItem> = _selectedCard

    init {
        getPopularMovies()
    }


    private fun getPopularMovies() {
        viewModelScope.launch {
            _status.value = TmdbApiStatus.LOADING
            try {
                _popularMovies.value = MoviesDatabaseApi.retrofitService.getPopularMovies()
                _status.value = TmdbApiStatus.DONE
            } catch (e: Exception) {
                _status.value = TmdbApiStatus.ERROR
            }
        }
    }

    fun onMovieClicked(movie: MovieListItem) {
        _selectedCard.value = movie
    }
}