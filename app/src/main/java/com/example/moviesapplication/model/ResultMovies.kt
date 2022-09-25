package com.example.moviesapplication.model

/**
 * Represents the response from the API.
 *
 * @property page The page.
 * @property results The list of movies retrieved.
 */
data class ResultMovies(val page: Int, val results: List<Movie>)