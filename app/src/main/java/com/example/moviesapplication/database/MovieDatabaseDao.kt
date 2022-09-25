package com.example.moviesapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDatabaseDao {

    /**
     * Inserts new favourite movie.
     *
     * @param movie the movie that should be inserted.
     */
    @Insert
    fun insert(movie: FavouriteMovie)

    /**
     * Gets all favourite movies from the database.
     *
     * @return list of all favourite movies.
     */
    @Query("SELECT * FROM favourite_movies_table ORDER BY movieId DESC")
    fun getAllFavouriteMovies(): LiveData<List<FavouriteMovie>>

    /**
     * Deletes the movie with the given id.
     *
     * @param id the movie id.
     */
    @Query("DELETE FROM favourite_movies_table WHERE movieId =:id")
    fun deleteMovie(id: Long)

    /**
     * Checks whether the movie with the given id is already in the database.
     *
     * @param id the movie id.
     */
    @Query("SELECT EXISTS (SELECT * FROM favourite_movies_table WHERE movieId =:id ORDER BY movieId DESC LIMIT 1)")
    fun isFavourite(id: Long): Boolean
}