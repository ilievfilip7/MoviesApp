package com.example.moviesapplication.ui.movie_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moviesapplication.R
import com.example.moviesapplication.database.FavouriteMovie
import com.example.moviesapplication.database.MovieDatabase
import com.example.moviesapplication.databinding.ActivityMovieDetailsBinding
import com.example.moviesapplication.model.Movie
import com.example.moviesapplication.ui.movies_preview.MoviesAdapter

/**
 * Activity for displaying details about certain movie.
 */
class MovieDetailsActivity : AppCompatActivity() {

    companion object {
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
    }

    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var adapter: MoviesAdapter
    private lateinit var viewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedIntent = intent
        val movie = receivedIntent.getParcelableExtra<Movie>("movie")
        val favouriteMovie = movie?.let {
            FavouriteMovie(
                it.id?.toLong() ?: 278,
                it.title ?: "",
                BASE_IMAGE_URL + movie.posterPath,
                it.voteAverage ?: 0.0f,
                it.overview ?: ""
            )
        }

        val dataSource = MovieDatabase.getInstance(application).movieDatabaseDao
        val viewModelFactory =
            MovieDetailsViewModelFactory(favouriteMovie!!, dataSource)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(MovieDetailsViewModel::class.java)

        adapter = MoviesAdapter(this)
        binding.similarMoviesRecyclerView.adapter = adapter
        binding.similarMoviesRecyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        viewModel.getSimilarMovies(favouriteMovie.movieId.toInt())
        viewModel.listOfSimilarMovies.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter.movies = it
            }
        })

        binding.title.text = favouriteMovie.title
        binding.overview.text = favouriteMovie.overview
        binding.ratingBar.rating = favouriteMovie.voteAverage

        Glide.with(this)
            .load(BASE_IMAGE_URL + favouriteMovie.imageUrl)
            .fallback(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.imagePoster)

        binding.favouriteImage.setOnClickListener {
            viewModel.addOrDeleteMovieFromFavorites(favouriteMovie.movieId)
        }

        viewModel.isMovieAddedToFavourites.observe(this, Observer {
            if (it) {
                binding.favouriteImage.setImageResource(R.drawable.favourite_filled)
            } else {
                binding.favouriteImage.setImageResource(R.drawable.favourite_empty)
            }
        })
    }
}