package com.example.tmdb_alkemy.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb_alkemy.databinding.MainListItemBinding
import com.example.tmdb_alkemy.model.MovieListItem

class MainListAdapter(private val clickListener: MovieListener) : ListAdapter<MovieListItem, MainListAdapter.MovieViewHolder>(DiffCallback) {

    class MovieViewHolder(
        private var binding: MainListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: MovieListener, movie: MovieListItem) {
            binding.clickListener = clickListener
            binding.movie = movie
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MovieListItem>() {
        override fun areItemsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
            return oldItem.title  == newItem.title
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
        holder.bind(clickListener, movie)
    }

}

class MovieListener(val clickListener: (movie: MovieListItem) -> Unit) {
    fun onClick(movie: MovieListItem) = clickListener(movie)
}