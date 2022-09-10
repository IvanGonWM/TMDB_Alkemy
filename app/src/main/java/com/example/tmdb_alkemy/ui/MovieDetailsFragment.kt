package com.example.tmdb_alkemy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tmdb_alkemy.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment: Fragment() {

    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentMovieDetailsBinding.inflate(inflater)
        binding.viewModel = viewModel
        return binding.root
    }
}