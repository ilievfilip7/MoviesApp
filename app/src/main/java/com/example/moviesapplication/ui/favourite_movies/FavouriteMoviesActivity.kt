package com.example.moviesapplication.ui.favourite_movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesapplication.database.MovieDatabase
import com.example.moviesapplication.databinding.ActivityFavouriteMoviesBinding

/**
 * Activity for displaying the favourite movies of the user.
 */
class FavouriteMoviesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavouriteMoviesBinding
    private lateinit var adapter: FavouriteMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataSource = MovieDatabase.getInstance(application).movieDatabaseDao
        val viewModelFactory = FavouriteMoviesViewModelFactory(dataSource)
        val favouriteMoviesViewModel =
            ViewModelProvider(this, viewModelFactory).get(FavouriteMoviesViewModel::class.java)

        adapter = FavouriteMoviesAdapter(this)
        binding.favouriteMoviesRecyclerView.adapter = adapter
        binding.favouriteMoviesRecyclerView.layoutManager = GridLayoutManager(this, 3)

        favouriteMoviesViewModel.listOfFavouriteMovies.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter.favouriteMovies = it
            }
        })
    }
}