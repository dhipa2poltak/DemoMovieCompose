package com.dpfht.demomoviecompose.domain.repository

import com.dpfht.demomoviecompose.domain.entity.MovieDomain
import com.dpfht.demomoviecompose.domain.entity.MovieEntity
import com.dpfht.demomoviecompose.domain.entity.db_entity.FavoriteMovieDBEntity

interface AppRepository {

  suspend fun getPopularMovies(page: Int): MovieDomain
  suspend fun getMovieDetails(movieId: Int): MovieEntity
  suspend fun searchMovie(query: String, page: Int): MovieDomain

  suspend fun getAllFavoriteMovies(): List<FavoriteMovieDBEntity>
  suspend fun getFavoriteMovie(movieId: Int): FavoriteMovieDBEntity?
  suspend fun addFavoriteMovie(movie: MovieEntity): FavoriteMovieDBEntity
  suspend fun deleteFavoriteMovie(movie: MovieEntity)
}
