package com.example.tmdb_alkemy.ui.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb_alkemy.model.MovieDetails
import com.example.tmdb_alkemy.network.FacadeRepository
import com.example.tmdb_alkemy.ui.main_list.TmdbApiStatus
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val id: Int) : ViewModel() {

    private val _status = MutableLiveData<TmdbApiStatus>()
    val status: LiveData<TmdbApiStatus> = _status

    private val _movie = MutableLiveData<MovieDetails>()
    val movie: LiveData<MovieDetails> = _movie

    init {
        getMovieDetails()
    }

    private fun getMovieDetails() {
        viewModelScope.launch {
            _status.value = TmdbApiStatus.LOADING
            try {
                val movieResult = FacadeRepository.getOpenedMovieDetails(id)
                _status.value = TmdbApiStatus.DONE
                _movie.value = movieResult
            } catch (e: Exception) {
                _status.value = TmdbApiStatus.ERROR
                _movie.value = MovieDetails()
            }

        }
    }
}