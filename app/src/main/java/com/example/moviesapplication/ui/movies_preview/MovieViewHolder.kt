package com.example.moviesapplication.ui.movies_preview

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapplication.R
import com.example.moviesapplication.databinding.MovieItemBinding
import com.example.moviesapplication.model.Movie
import com.example.moviesapplication.ui.movie_details.MovieDetailsActivity

/**
 * View holder for the movies displayed in the adapter.
 */
class MovieViewHolder(private val binding: MovieItemBinding, private val context: Context) :
    RecyclerView.ViewHolder(binding.root) {

    /**
     * Method that binds the items.
     *
     * @param item the movie.
     */
    fun bind(item: Movie) {
        binding.title.text = item.title
        binding.ratingBar.rating = item.voteAverage?.div(2) ?: 0.0f

        binding.root.setOnClickListener {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra("movie", item)
            context.startActivity(intent)
        }

        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w500/" + item.posterPath)
            .fallback(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.poster)
    }
}
