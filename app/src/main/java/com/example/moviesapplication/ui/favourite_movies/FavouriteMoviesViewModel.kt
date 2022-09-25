package com.example.moviesapplication.ui.favourite_movies

import androidx.lifecycle.ViewModel
import com.example.moviesapplication.database.MovieDatabaseDao

/**
 * View model for the Favourite details activity.
 */
class FavouriteMoviesViewModel(val database: MovieDatabaseDao) : ViewModel() {
    var listOfFavouriteMovies = database.getAllFavouriteMovies()
}