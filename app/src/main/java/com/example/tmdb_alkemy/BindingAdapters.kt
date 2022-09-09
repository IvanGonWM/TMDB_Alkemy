package com.example.tmdb_alkemy

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tmdb_alkemy.model.MovieListItem
import com.example.tmdb_alkemy.ui.MainListAdapter
import com.example.tmdb_alkemy.ui.TmdbApiStatus

private const val LIST_ITEM_POSTER_URL = "https://www.themoviedb.org/t/p/w220_and_h330_face"
private const val DETAILS_POSTER_URL = "https://www.themoviedb.org/t/p/w300_and_h450_bestv2"

@BindingAdapter("listItemPosterUrl")
fun bindListImage(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val imageUri = (LIST_ITEM_POSTER_URL + imageUrl).toUri().buildUpon().scheme("https").build()
        imageView.load(imageUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("detailsPosterUrl")
fun bindDetailsImage(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val imageUri = (DETAILS_POSTER_URL + imageUrl).toUri().buildUpon().scheme("https").build()
        imageView.load(imageUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    data: List<MovieListItem>?
) {
    val adapter = recyclerView.adapter as MainListAdapter
    adapter.submitList(data)
}

@BindingAdapter("tmdbApiStatus")
fun bindStatus(statusImageView: ImageView,
               status: TmdbApiStatus?) {
    when (status) {
        TmdbApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        TmdbApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        TmdbApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        else -> {return}
    }

}
