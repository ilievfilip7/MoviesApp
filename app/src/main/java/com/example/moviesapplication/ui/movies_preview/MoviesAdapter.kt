package com.example.moviesapplication.ui.movies_preview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapplication.databinding.MovieItemBinding
import com.example.moviesapplication.model.Movie

/**
 * Adapter for the movie items shown.
 */
class MoviesAdapter(private val context: Context) : RecyclerView.Adapter<MovieViewHolder>() {

    /**
     * The movies list.
     */
    var movies = ArrayList<Movie>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, context)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }
}