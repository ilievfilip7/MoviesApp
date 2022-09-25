package com.example.moviesapplication

import com.example.moviesapplication.database.FavouriteMovie
import com.example.moviesapplication.database.MovieDatabase
import com.example.moviesapplication.database.MovieDatabaseDao
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

class MovieDatabaseTest {

    private lateinit var movieDao: MovieDatabaseDao
    private lateinit var db: MovieDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, MovieDatabase::class.java)
            .allowMainThreadQueries() //just for testing
            .build()
        movieDao = db.movieDatabaseDao
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndCheckMovie() {
        val movie = FavouriteMovie(5000, "Titanic", "", 9.8f, "titanic overview")
        movieDao.insert(movie)
        val isFavourite = movieDao.isFavourite(5000)
        assertEquals(true, isFavourite)
    }
}

