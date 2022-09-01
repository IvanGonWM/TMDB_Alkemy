package com.example.tmdb_alkemy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tmdb_alkemy.R

class MainListFragment: Fragment() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAmphibianListBinding.inflate(inflater)
        // TODO: call the view model method that calls the amphibians api
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerView.adapter = MainListAdapter(AmphibianListener { amphibian ->
            viewModel.onAmphibianClicked(amphibian)
            findNavController()
                .navigate(R.id)
        })

        // Inflate the layout for this fragment
        return binding.root
    }


}