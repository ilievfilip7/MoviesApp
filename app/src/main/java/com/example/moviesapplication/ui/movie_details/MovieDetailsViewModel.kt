package com.example.moviesapplication.ui.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapplication.database.FavouriteMovie
import com.example.moviesapplication.database.MovieDatabaseDao
import com.example.moviesapplication.model.Movie
import com.example.moviesapplication.network.MoviesAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*

/**
 * View model for the movie details activity.
 */
class MovieDetailsViewModel(
    private val favouriteMovie: FavouriteMovie,
    val database: MovieDatabaseDao
) : ViewModel() {

    private val _listOfSimilarMovies: MutableLiveData<ArrayList<Movie>> =
        MutableLiveData<ArrayList<Movie>>()
    val listOfSimilarMovies: LiveData<ArrayList<Movie>> = _listOfSimilarMovies

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    val isMovieAddedToFavourites = MutableLiveData<Boolean>()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        coroutineScope.launch {
            isMovieAddedToFavourites.value = isFavourite(favouriteMovie.movieId)
        }
    }

    fun getSimilarMovies(id: Int) {
        compositeDisposable.add(
            MoviesAPI.retrofitService.getSimilarMoviesAsync(id = id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movies ->
                    _listOfSimilarMovies.value = movies.results as ArrayList<Movie>
                }, {
                    _listOfSimilarMovies.value = ArrayList()
                    println(it.message)
                })
        )
    }

    fun addOrDeleteMovieFromFavorites(id: Long) {
        coroutineScope.launch {
            isMovieAddedToFavourites.value = !isFavourite(id)
            withContext(Dispatchers.IO) {
                if (isMovieAddedToFavourites.value!!) {
                    database.insert(favouriteMovie)
                } else {
                    database.deleteMovie(id)
                }
            }
        }
    }

    private suspend fun isFavourite(id: Long): Boolean {
        return withContext(Dispatchers.IO) {
            database.isFavourite(id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        compositeDisposable.dispose()
    }
}