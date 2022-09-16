package com.example.tmdb_alkemy

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.content.res.Resources
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tmdb_alkemy.model.Genre
import com.example.tmdb_alkemy.model.MovieDetails
import com.example.tmdb_alkemy.model.MovieListItem
import com.example.tmdb_alkemy.ui.main_list.MainListAdapter
import com.example.tmdb_alkemy.ui.main_list.TmdbApiStatus
import com.google.android.material.progressindicator.CircularProgressIndicator
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.round

private const val LIST_ITEM_POSTER_URL = "https://www.themoviedb.org/t/p/w220_and_h330_face"
private const val DETAILS_POSTER_URL = "https://www.themoviedb.org/t/p/w300_and_h450_bestv2"

@BindingAdapter("posterUrl")
fun bindImage(
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


@BindingAdapter("listData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    data: List<MovieListItem>?
) {
    val adapter = recyclerView.adapter as MainListAdapter
    adapter.submitList(data)
}


@BindingAdapter("statusImage")
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


@BindingAdapter("statusText")
fun bindStatus(
    statusTextView: TextView,
    status: TmdbApiStatus?
) {
    when (status) {
        TmdbApiStatus.DONE -> {
            statusTextView.visibility = View.GONE
        }
        TmdbApiStatus.ERROR -> {
            statusTextView.visibility = View.VISIBLE
            statusTextView.setText(R.string.status_text_error)
        }
        TmdbApiStatus.LOADING -> {
            statusTextView.visibility = View.VISIBLE
            statusTextView.setText(R.string.status_text_loading)
        }
        else -> {
            return
        }
    }
}


@BindingAdapter("detailStatusText")
fun bindDetailStatus(
    statusTextView: TextView,
    status: TmdbApiStatus?
) {
    when (status) {
        TmdbApiStatus.DONE -> {
            statusTextView.visibility = View.GONE
        }
        TmdbApiStatus.ERROR -> {
            statusTextView.visibility = View.VISIBLE
            statusTextView.setText(R.string.status_text_error)
        }
        TmdbApiStatus.LOADING -> {
            statusTextView.visibility = View.VISIBLE
            statusTextView.setText(R.string.details_status_text_loading)
        }
        else -> {
            return
        }
    }
}


@SuppressLint("SetTextI18n")
@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("formatMovieDate")
fun bindMovieDate(
    dateTextView: TextView,
    unformattedDate: String?
) {

    val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = LocalDate.parse(unformattedDate, format)

    val formattedDay = date.dayOfMonth.toString()
    val formattedMonth  = date.month.toString().lowercase().replaceFirstChar { it.uppercaseChar() }
    val formattedYear = date.year.toString()

    dateTextView.text = "$formattedMonth $formattedDay, $formattedYear"
}


@BindingAdapter("setDetailsImage")
fun bindDetailsImage(
    imageView: ImageView,
    posterPath: String?
) {
    posterPath?.let {
        val imageUri =
            (DETAILS_POSTER_URL + posterPath).toUri().buildUpon().scheme("https")
                .build()
        imageView.load(imageUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}


@BindingAdapter("setMovieTitle")
fun bindTitle(
    textView: TextView,
    title: String?
) {
    if (title != null)
        textView.text = title
}


@BindingAdapter("setGenreSubtitle")
fun bindGenreSubtitle(
    genreTextView: TextView,
    genres: List<Genre>?
) {
    if (genres != null) {
        genreTextView.text = genres.joinToString { it.name }
    }
}


@SuppressLint("SetTextI18n")
@BindingAdapter("setUserScore")
fun bindUserScore(
    scoreTextView: TextView,
    voteAverage: Double?
) {
    if (voteAverage != null) {
        scoreTextView.text = "User score:\n ${round(voteAverage)} / 10"
    }
}


@SuppressLint("SetTextI18n")
@BindingAdapter("setMixedData")
fun bindMixedData(
    mixedDataTextView: TextView,
    movie: LiveData<MovieDetails>?
) {
    if (movie?.value?.releaseDate != null
        && movie.value?.originalLanguage != null
    ) {
        mixedDataTextView.text = "${movie.value!!.releaseDate} \n Lang: ${movie.value!!.originalLanguage}"
    }
}

@BindingAdapter("setTagline")
fun bindTagline(
    taglineTextView: TextView,
    tagline: String?
) {
    if (tagline != null) taglineTextView.text = tagline

}

@BindingAdapter("setOverview")
fun bindOverview(
    overviewTextView: TextView,
    overview: String?
) {
    if (overview != null) {
        overviewTextView.text = overview
    }
}