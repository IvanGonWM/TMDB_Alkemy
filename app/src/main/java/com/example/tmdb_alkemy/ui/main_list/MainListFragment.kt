package com.example.tmdb_alkemy.ui.main_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tmdb_alkemy.R
import com.example.tmdb_alkemy.databinding.FragmentMainListBinding
import java.lang.Exception

class MainListFragment : Fragment() {

    private val viewModel: MainListViewModel by viewModels()
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    private val swipeLayout: SwipeRefreshLayout?
        get() = view?.findViewById(R.id.swipe_layout)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainListBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.mainRecyclerView.adapter = MainListAdapter()
        viewModel.movieList.observe(viewLifecycleOwner) {
            (binding.mainRecyclerView.adapter as MainListAdapter).notifyDataSetChanged()
            isLoading = false
            swipeLayout?.isRefreshing = false
            isLastPage = viewModel.isLastPage

        }

        binding.mainRecyclerView.addOnScrollListener(object :
            MainListScrollListener(binding.mainRecyclerView.layoutManager as LinearLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                viewModel.getNextPopularMovies()
            }

        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        swipeLayout?.setOnRefreshListener {
            try {
                viewModel.refreshMovies()
            }
            catch (e: Exception){
                swipeLayout?.isRefreshing = false
            }
        }


    }
}


