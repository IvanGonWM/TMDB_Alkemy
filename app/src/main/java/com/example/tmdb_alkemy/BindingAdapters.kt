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
import androidx.lifecycle.LiveData
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
    dateTextView.text = ""
    /*
    val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = LocalDate.parse(unformattedDate, format)

    Log.d("day", date.dayOfMonth.toString() )
    Log.d("month", date.month.toString().lowercase().replaceFirstChar { it.uppercaseChar() } )
    Log.d("year", date.year.toString() )
    dateTextView.text = "ola"
    /*Resources.getSystem()
    .getString(R.string.formatted_date,
        date.month.toString().lowercase().replaceFirstChar { it.uppercaseChar() },
        date.dayOfMonth.toString(),
        date.year.toString())*/*/
}

/**dastards**/


@BindingAdapter("setDetailsImage")
fun bindDetailsImage(
    imageView: ImageView,
    movie: LiveData<MovieDetails>?
) {
    if (movie?.value?.posterPath != null) {
        movie.value!!.posterPath.let {
            val imageUri =
                (DETAILS_POSTER_URL + movie.value!!.posterPath).toUri().buildUpon().scheme("https")
                    .build()
            imageView.load(imageUri) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
        }
    }
}


@BindingAdapter("setMovieTitle")
fun bindTitle(
    textView: TextView,
    movie: LiveData<MovieDetails>?
) {
    if (movie?.value?.title != null)
        textView.text = movie.value!!.title
}


@BindingAdapter("setGenreSubtitle")
fun bindGenreSubtitle(
    genreTextView: TextView,
    movie: LiveData<MovieDetails>?
) {
    if (movie?.value?.voteAverage != null) {
        genreTextView.text = movie.value!!.genres.joinToString { it.name }
    }
}


@BindingAdapter("setUserScore")
fun bindUserScore(
    scoreTextView: TextView,
    movie: LiveData<MovieDetails>?
) {
    if (movie?.value?.voteAverage != null) {
        scoreTextView.text = "User score:\n ${movie.value!!.voteAverage}/10"
            //Resources.getSystem()
            //.getString(R.string.user_score, movie.value!!.voteAverage)
    }
}


@BindingAdapter("setMixedData")
fun bindMixedData(
    mixedDataTextView: TextView,
    movie: LiveData<MovieDetails>?
) {
    if (movie?.value?.releaseDate != null
        && movie.value?.originalLanguage != null
    ) {
        mixedDataTextView.text = "${movie.value!!.releaseDate} \n ${movie.value!!.originalLanguage}"

            /*Resources.getSystem()
            .getString(
                R.string.mixed_data,
                movie.value!!.releaseDate,
                movie.value!!.originalLanguage
            )*/
    }
}

@BindingAdapter("setTagline")
fun bindTagline(
    taglineTextView: TextView,
    movie: LiveData<MovieDetails>?
) {
    if (movie?.value?.tagline != null) {
        taglineTextView.text = movie.value!!.tagline
    }
}

@BindingAdapter("setOverview")
fun bindOverview(
    overviewTextView: TextView,
    movie: LiveData<MovieDetails>?
) {
    if (movie?.value?.description != null) {
        overviewTextView.text = movie.value!!.description
    }
}