package com.example.tmdb_alkemy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tmdb_alkemy.R
import com.example.tmdb_alkemy.databinding.FragmentMainListBinding
import com.example.tmdb_alkemy.databinding.MainListItemBinding

class MainListFragment: Fragment() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.mainRecyclerView.adapter = MainListAdapter(MovieListener { movie ->
            viewModel.onMovieClicked(movie)
            findNavController()
                .navigate(R.id.action_mainListFragment_to_movieDetailsFragment)
        })

        // Inflate the layout for this fragment
        return binding.root
    }


}