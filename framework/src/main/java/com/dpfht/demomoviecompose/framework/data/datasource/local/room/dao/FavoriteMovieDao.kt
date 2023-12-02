package com.dpfht.demomoviecompose.framework.data.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dpfht.demomoviecompose.framework.data.datasource.local.room.model.FavoriteMovieDBModel

@Dao
interface FavoriteMovieDao {

  @Query("SELECT * FROM tbl_favorite_movies")
  fun getAllFavoriteMovies(): List<FavoriteMovieDBModel>

  @Query("SELECT * FROM tbl_favorite_movies WHERE movie_id = :movieId")
  suspend fun getFavoriteMovie(movieId: Int): List<FavoriteMovieDBModel>

  @Insert
  suspend fun insertFavoriteMovie(movie: FavoriteMovieDBModel): Long

  @Query("DELETE FROM tbl_favorite_movies WHERE movie_id = :movieId")
  suspend fun deleteFavoriteMovie(movieId: Int)
}
