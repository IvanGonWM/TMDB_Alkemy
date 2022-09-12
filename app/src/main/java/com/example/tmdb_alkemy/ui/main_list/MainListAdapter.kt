package com.example.tmdb_alkemy.ui.main_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb_alkemy.databinding.MainListItemBinding
import com.example.tmdb_alkemy.model.MovieListItem

class MainListAdapter : ListAdapter<MovieListItem, MainListAdapter.MovieViewHolder>
    (DiffCallback) {

    class MovieViewHolder(
        private var binding: MainListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieListItem) {
            binding.movie = movie
            binding.itemCard.setOnClickListener {
                this.itemView.findNavController().navigate(
                    MainListFragmentDirections
                        .actionMainListFragmentToMovieDetailsFragment(movieId = movie.id)
                )
            }
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MovieListItem>() {
        override fun areItemsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
            return oldItem.title == newItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(
            MainListItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

}