package com.example.moviesapplication.ui.favourite_movies

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapplication.R
import com.example.moviesapplication.database.FavouriteMovie
import com.example.moviesapplication.databinding.MovieItemBinding
import com.example.moviesapplication.model.Movie
import com.example.moviesapplication.ui.movie_details.MovieDetailsActivity

/**
 * View holder for the favourite movies displayed in the adapter.
 */
class FavouriteMovieViewHolder(
    private val binding: MovieItemBinding,
    private val context: Context
) :
    RecyclerView.ViewHolder(binding.root) {

    /**
     * Method that binds the items.
     *
     * @param item the movie.
     */
    fun bind(item: FavouriteMovie) {
        binding.root.setOnClickListener {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            val movie = Movie(
                item.movieId.toInt(),
                item.title,
                item.imageUrl,
                item.voteAverage,
                item.overview
            )
            intent.putExtra("movie", movie)
            context.startActivity(intent)
        }

        binding.title.text = item.title
        binding.ratingBar.rating = item.voteAverage.div(2)

        Glide.with(context)
            .load(item.imageUrl)
            .fallback(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.poster)
    }
}
