package com.example.moviesapplication.ui.movies_preview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapplication.databinding.ActivityMoviesBinding
import com.example.moviesapplication.ui.favourite_movies.FavouriteMoviesActivity
import kotlin.reflect.KFunction1

/**
 * Activity for searching and displaying various lists of movies.
 */
class MoviesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviesBinding
    private lateinit var viewModel: MoviesViewModel
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var searchedMoviesAdapter: MoviesAdapter
    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var nowPlayingMoviesAdapter: MoviesAdapter
    private lateinit var upcomingMoviesAdapter: MoviesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)

        setupSearchView()

        popularMoviesAdapter = MoviesAdapter(this)
        searchedMoviesAdapter = MoviesAdapter(this)
        topRatedMoviesAdapter = MoviesAdapter(this)
        nowPlayingMoviesAdapter = MoviesAdapter(this)
        upcomingMoviesAdapter = MoviesAdapter(this)

        setupAdapters(popularMoviesAdapter, binding.popularMoviesRecyclerView)
        setupAdapters(searchedMoviesAdapter, binding.searchedMoviesRecyclerView)
        setupAdapters(topRatedMoviesAdapter, binding.topRatedMoviesRecyclerView)
        setupAdapters(nowPlayingMoviesAdapter, binding.nowPlayingMoviesRecyclerView)
        setupAdapters(upcomingMoviesAdapter, binding.upcomingMoviesRecyclerView)

        addOnScrollListener(binding.popularMoviesRecyclerView) { viewModel.getPopularMovies() }
        addOnScrollListener(binding.topRatedMoviesRecyclerView) { viewModel.getTopRatedMovies() }
        addOnScrollListener(binding.nowPlayingMoviesRecyclerView) { viewModel.getNowPlayingMovies() }
        addOnScrollListener(binding.upcomingMoviesRecyclerView) { viewModel.getUpcomingMovies() }
        addOnScrollListener(binding.searchedMoviesRecyclerView) {
            viewModel.getMoviesByTitle(binding.searchView.query.toString())
        }

        viewModel.listOfSearchedMovies.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.searchResultsTitle.visibility = View.VISIBLE
                searchedMoviesAdapter.movies = it
            }
        })

        viewModel.listOfPopularMovies.observe(this, Observer {
            if (it.isNotEmpty()) {
                popularMoviesAdapter.movies = it
            }
        })

        viewModel.listOfTopRatedMovies.observe(this, Observer {
            if (it.isNotEmpty()) {
                topRatedMoviesAdapter.movies = it
            }
        })

        viewModel.listOfNowPlayingMovies.observe(this, Observer {
            if (it.isNotEmpty()) {
                nowPlayingMoviesAdapter.movies = it
            }
        })

        viewModel.listOfUpcomingMovies.observe(this, Observer {
            if (it.isNotEmpty()) {
                upcomingMoviesAdapter.movies = it
            }
        })

        viewModel.status.observe(this, Observer {
            when (it) {
                MovieApiStatus.SUCCESS -> {
                    binding.popularMoviesRecyclerView.visibility = View.VISIBLE
                    binding.statusImage.visibility = View.GONE
                    binding.popularProgressBar.visibility = View.GONE
                }
                MovieApiStatus.ERROR -> {
                    binding.popularMoviesRecyclerView.visibility = View.GONE
                    binding.popularMoviesTitle.visibility = View.GONE
                    binding.topRatedMoviesTitle.visibility = View.GONE
                    binding.upcomingMoviesTitle.visibility = View.GONE
                    binding.nowPlayingMoviesTitle.visibility = View.GONE
                    binding.searchResultsTitle.visibility = View.GONE
                    binding.statusImage.visibility = View.VISIBLE
                    binding.popularProgressBar.visibility = View.GONE
                }
                MovieApiStatus.LOADING -> {
                    binding.popularMoviesRecyclerView.visibility = View.GONE
                    binding.statusImage.visibility = View.GONE
                    binding.popularProgressBar.visibility = View.VISIBLE
                }
            }
        })

        binding.favourites.setOnClickListener {
            startActivity(Intent(this, FavouriteMoviesActivity::class.java))
        }

    }

    private fun setupAdapters(adapter: MoviesAdapter, recyclerView: RecyclerView) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun addOnScrollListener(recyclerView: RecyclerView, function: () -> Unit) {
        recyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollHorizontally(1)) {
                    function()
                }
            }
        })
    }

    private fun setupSearchView() {
        binding.searchView.maxWidth = Integer.MAX_VALUE
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getMoviesByTitle(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })

        binding.searchView.setOnCloseListener {
            binding.searchResultsTitle.visibility = View.GONE
            searchedMoviesAdapter.movies = arrayListOf()
            false
        }
    }
}