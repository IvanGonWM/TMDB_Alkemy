package com.example.tmdb_alkemy.ui.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tmdb_alkemy.R
import com.example.tmdb_alkemy.databinding.FragmentMovieDetailsBinding
import com.example.tmdb_alkemy.ui.main_list.MainListFragmentDirections

class MovieDetailsFragment : Fragment() {

    private lateinit var id: Number

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            id = it.getInt("movieId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentMovieDetailsBinding.inflate(inflater)
        val viewModel = MovieDetailsViewModel(id.toInt())
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipeLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_layout)
        swipeLayout.setOnRefreshListener {
            val navController = findNavController()
            navController.run {
                popBackStack()
                navigate(
                    MainListFragmentDirections
                        .actionMainListFragmentToMovieDetailsFragment(movieId = id.toInt())
                )
            }

            swipeLayout.isRefreshing = false
        }
    }
}