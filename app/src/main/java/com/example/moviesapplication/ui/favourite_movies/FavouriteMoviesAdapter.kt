package com.example.moviesapplication.ui.favourite_movies

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapplication.database.FavouriteMovie
import com.example.moviesapplication.databinding.MovieItemBinding

/**
 * Adapter for displaying the favourite movies.
 */
class FavouriteMoviesAdapter(private val context: Context) :
    RecyclerView.Adapter<FavouriteMovieViewHolder>() {

    /**
     * The movies list.
     */
    var favouriteMovies: List<FavouriteMovie> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteMovieViewHolder {
        val binding =
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteMovieViewHolder(binding, context)
    }

    override fun getItemCount(): Int {
        return favouriteMovies.size
    }

    override fun onBindViewHolder(holder: FavouriteMovieViewHolder, position: Int) {
        holder.bind(favouriteMovies[position])
    }
}