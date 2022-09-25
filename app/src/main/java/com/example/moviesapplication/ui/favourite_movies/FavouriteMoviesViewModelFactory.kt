package com.example.moviesapplication.ui.favourite_movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapplication.database.MovieDatabaseDao

/**
 * This is a ViewModel Factory for creating the FavouriteMoviesViewModel.
 *
 * Provides the MovieDatabaseDao to the ViewModel.
 */
class FavouriteMoviesViewModelFactory(
    private val dataSource: MovieDatabaseDao,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouriteMoviesViewModel::class.java)) {
            return FavouriteMoviesViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}