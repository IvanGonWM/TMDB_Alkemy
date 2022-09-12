package com.example.tmdb_alkemy

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.content.res.Resources
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tmdb_alkemy.model.Genre
import com.example.tmdb_alkemy.model.MovieDetails
import com.example.tmdb_alkemy.model.MovieListItem
import com.example.tmdb_alkemy.ui.main_list.MainListAdapter
import com.example.tmdb_alkemy.ui.main_list.TmdbApiStatus
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val LIST_ITEM_POSTER_URL = "https://www.themoviedb.org/t/p/w220_and_h330_face"
private const val DETAILS_POSTER_URL = "https://www.themoviedb.org/t/p/w300_and_h450_bestv2"

@BindingAdapter("listItemPosterUrl")
fun bindListImage(
    imageView: ImageView,
    imageUrl: String?
) {
    imageUrl?.let {
        val imageUri = (LIST_ITEM_POSTER_URL + imageUrl).toUri().buildUpon().scheme("https").build()
        imageView.load(imageUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("detailsPosterUrl")
fun bindDetailsImage(
    imageView: ImageView,
    imageUrl: String?
) {
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
fun bindStatus(
    statusImageView: ImageView,
    status: TmdbApiStatus?
) {
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
        else -> {
            return
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("formatMovieDate")
fun bindMovieDate(
    dateTextView: TextView,
    unformattedDate: String?
) {
    val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = LocalDate.parse(unformattedDate, format)

    Log.d("day", date.dayOfMonth.toString() )
    Log.d("month", date.month.toString().lowercase().replaceFirstChar { it.uppercaseChar() } )
    Log.d("year", date.year.toString() )
    dateTextView.text = ""
        /*Resources.getSystem()
        .getString(R.string.formatted_date,
            date.month.toString().lowercase().replaceFirstChar { it.uppercaseChar() },
            date.dayOfMonth.toString(),
            date.year.toString())*/
}

@BindingAdapter("formatUserScore")
fun bindUserScore(
    scoreTextView: TextView,
    score: Double?,
) {
    scoreTextView.text = Resources.getSystem()
        .getString(R.string.user_score, score)
}

@BindingAdapter("genreSubtitle")
fun bindGenreSubtitle(
    genreTextView: TextView,
    genres: List<Genre>?
) {
    genreTextView.text = genres?.joinToString { it.name }
}

@BindingAdapter("mixedData")
fun bindMixedData(
    mixedDataTextView: TextView,
    movieDetailsItem: MovieDetails?
) {
    mixedDataTextView.text = Resources.getSystem()
        .getString(
            R.string.mixed_data,
            movieDetailsItem?.releaseDate,
            movieDetailsItem?.originalLanguage
        )
}