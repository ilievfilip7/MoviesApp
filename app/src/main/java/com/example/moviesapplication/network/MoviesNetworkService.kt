package com.example.moviesapplication.network

import com.example.moviesapplication.model.ResultMovies
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "1d5ffdca0290728cf8bf5051ce4afcfd"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface MoviesNetworkService {

    @GET("movie/popular")
    fun getPopularMoviesAsync(
        @Query("api_key") api_key: String = API_KEY,
        @Query("page") page: Int
    ): Observable<ResultMovies>

    @GET("movie/top_rated")
    fun getTopRatedMoviesAsync(
        @Query("api_key") api_key: String = API_KEY,
        @Query("page") page: Int
    ): Observable<ResultMovies>

    @GET("movie/now_playing")
    fun getNowPlayingMoviesAsync(
        @Query("api_key") api_key: String = API_KEY,
        @Query("page") page: Int
    ): Observable<ResultMovies>

    @GET("movie/upcoming")
    fun getUpcomingMoviesAsync(
        @Query("api_key") api_key: String = API_KEY,
        @Query("page") page: Int
    ): Observable<ResultMovies>

    @GET("movie/{movie_id}/similar")
    fun getSimilarMoviesAsync(
        @Path("movie_id") id: Int,
        @Query("api_key") api_key: String = API_KEY
    ): Observable<ResultMovies>

    @GET("search/movie")
    fun getMoviesByTitleAsync(
        @Query("api_key") api_key: String = API_KEY,
        @Query("query") query: String = "",
        @Query("page") page: Int
    ): Observable<ResultMovies>
}

object MoviesAPI {
    val retrofitService: MoviesNetworkService by lazy {
        retrofit.create(MoviesNetworkService::class.java)
    }
}
