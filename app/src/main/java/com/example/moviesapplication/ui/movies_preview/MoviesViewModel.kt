package com.example.moviesapplication.ui.movies_preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapplication.model.Movie
import com.example.moviesapplication.network.MoviesAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * The API response status.
 */
enum class MovieApiStatus {
    /** API request is in loading state. */
    LOADING,

    /** API request is in error state. */
    ERROR,

    /** API request is in success state. */
    SUCCESS
}

/**
 * View model for the movie activity.
 */
class MoviesViewModel : ViewModel() {

    private val _listOfPopularMovies: MutableLiveData<ArrayList<Movie>> =
        MutableLiveData<ArrayList<Movie>>()
    val listOfPopularMovies: LiveData<ArrayList<Movie>> = _listOfPopularMovies

    private val _listOfTopRatedMovies: MutableLiveData<ArrayList<Movie>> =
        MutableLiveData<ArrayList<Movie>>()
    val listOfTopRatedMovies: LiveData<ArrayList<Movie>> = _listOfTopRatedMovies

    private val _listOfNowPlayingMovies: MutableLiveData<ArrayList<Movie>> =
        MutableLiveData<ArrayList<Movie>>()
    val listOfNowPlayingMovies: LiveData<ArrayList<Movie>> = _listOfNowPlayingMovies

    private val _listOfUpcomingMovies: MutableLiveData<ArrayList<Movie>> =
        MutableLiveData<ArrayList<Movie>>()
    val listOfUpcomingMovies: LiveData<ArrayList<Movie>> = _listOfUpcomingMovies

    private val _listOfSearchedMovies: MutableLiveData<ArrayList<Movie>> =
        MutableLiveData<ArrayList<Movie>>()
    val listOfSearchedMovies: LiveData<ArrayList<Movie>> = _listOfSearchedMovies

    private val _status = MutableLiveData<MovieApiStatus>()
    val status: LiveData<MovieApiStatus>
        get() = _status

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var mPopularMoviesPageNumber: Int = 1
    private var mTopRatedMoviesPageNumber: Int = 1
    private var mNowPlayingMoviesPageNumber: Int = 1
    private var mUpcomingMoviesPageNumber: Int = 1
    private var mSearchedMoviesPageNumber: Int = 1

    init {
        getPopularMovies()
        getTopRatedMovies()
        getNowPlayingMovies()
        getUpcomingMovies()
    }

    fun getPopularMovies() {
        compositeDisposable.add(
            MoviesAPI.retrofitService.getPopularMoviesAsync(page = mPopularMoviesPageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movies ->
                    _status.value = MovieApiStatus.LOADING
                    _listOfPopularMovies.value = if (mPopularMoviesPageNumber == 1) {
                        movies.results as ArrayList<Movie>
                    } else {
                        _listOfPopularMovies.value?.plus(movies.results) as ArrayList<Movie>
                    }
                    _status.value = MovieApiStatus.SUCCESS
                    mPopularMoviesPageNumber += 1
                }, {
                    _status.value = MovieApiStatus.ERROR
                    _listOfPopularMovies.value = ArrayList()
                    println(it.message)
                })
        )
    }

    fun getTopRatedMovies() {
        compositeDisposable.add(
            MoviesAPI.retrofitService.getTopRatedMoviesAsync(page = mTopRatedMoviesPageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movies ->
                    _listOfTopRatedMovies.value = if (mTopRatedMoviesPageNumber == 1) {
                        movies.results as ArrayList<Movie>
                    } else {
                        _listOfTopRatedMovies.value?.plus(movies.results) as ArrayList<Movie>
                    }
                    mTopRatedMoviesPageNumber += 1
                }, {
                    _listOfTopRatedMovies.value = ArrayList()
                    println(it.message)
                })
        )
    }

    fun getNowPlayingMovies() {
        compositeDisposable.add(
            MoviesAPI.retrofitService.getNowPlayingMoviesAsync(page = mNowPlayingMoviesPageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movies ->
                    _listOfNowPlayingMovies.value = if (mNowPlayingMoviesPageNumber == 1) {
                        movies.results as ArrayList<Movie>
                    } else {
                        _listOfNowPlayingMovies.value?.plus(movies.results) as ArrayList<Movie>
                    }
                    mNowPlayingMoviesPageNumber += 1
                }, {
                    _listOfNowPlayingMovies.value = ArrayList()
                    println(it.message)
                })
        )
    }

    fun getUpcomingMovies() {
        compositeDisposable.add(
            MoviesAPI.retrofitService.getUpcomingMoviesAsync(page = mUpcomingMoviesPageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movies ->
                    _listOfUpcomingMovies.value = if (mUpcomingMoviesPageNumber == 1) {
                        movies.results as ArrayList<Movie>
                    } else {
                        _listOfUpcomingMovies.value?.plus(movies.results) as ArrayList<Movie>
                    }
                    mUpcomingMoviesPageNumber += 1

                }, {
                    _listOfUpcomingMovies.value = ArrayList()
                    println(it.message)
                })
        )
    }

    fun getMoviesByTitle(query: String?) {
        compositeDisposable.add(
            MoviesAPI.retrofitService.getMoviesByTitleAsync(
                query = query ?: "",
                page = mSearchedMoviesPageNumber
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movies ->
                    _listOfSearchedMovies.value = if (mSearchedMoviesPageNumber == 1) {
                        movies.results as ArrayList<Movie>
                    } else {
                        _listOfSearchedMovies.value?.plus(movies.results) as ArrayList<Movie>
                    }
                    mSearchedMoviesPageNumber += 1

                }, {
                    _listOfSearchedMovies.value = ArrayList()
                    println(it.message)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}