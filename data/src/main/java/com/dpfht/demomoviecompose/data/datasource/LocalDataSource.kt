package com.dpfht.demomoviecompose.data.datasource

import com.dpfht.demomoviecompose.domain.entity.MovieEntity
import com.dpfht.demomoviecompose.domain.entity.db_entity.FavoriteMovieDBEntity

interface LocalDataSource {

  suspend fun getAllFavoriteMovies(): List<FavoriteMovieDBEntity>
  suspend fun getFavoriteMovie(movieId: Int): FavoriteMovieDBEntity?
  suspend fun addFavoriteMovie(movie: MovieEntity): FavoriteMovieDBEntity
  suspend fun deleteFavoriteMovie(movie: MovieEntity)
}
