package com.example.moviesapplication.ui.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapplication.database.FavouriteMovie
import com.example.moviesapplication.database.MovieDatabaseDao

/**
 * This is a ViewModel Factory for creating the MovieDetailsViewModel.
 *
 * Provides the currently opened movie the MovieDatabaseDao to the ViewModel.
 */
class MovieDetailsViewModelFactory(
    private val favouriteMovie: FavouriteMovie,
    private val dataSource: MovieDatabaseDao,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(favouriteMovie, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}