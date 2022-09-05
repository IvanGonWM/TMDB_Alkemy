package com.example.tmdb_alkemy.network

import com.example.tmdb_alkemy.model.MovieDetails
import com.example.tmdb_alkemy.model.MovieList
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/"
private const val API_KEY = "895dc3f394a1ac9177a5cfef55daf32d"

enum class tmdbApiStatus { LOADING, ERROR, DONE }

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MoviesApiService {

    @GET("https://api.themoviedb.org/3/movie/popular?api_key=$API_KEY&language=en-US&")
    suspend fun getPopularMovies(@Query("page") page: Int = 1): MovieList

    @GET("3/movie/{id}?api_key=${API_KEY}&language=en-US")
    suspend fun getMovieDetails(@Path("id") id: Int): MovieDetails
}

object MoviesDatabaseApi {
    val retrofitService : MoviesApiService by lazy {
        retrofit.create(MoviesApiService::class.java)
    }
}