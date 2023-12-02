package com.dpfht.demomoviecompose.data.repository

import com.dpfht.demomoviecompose.data.datasource.LocalDataSource
import com.dpfht.demomoviecompose.data.datasource.RemoteDataSource
import com.dpfht.demomoviecompose.domain.entity.MovieDomain
import com.dpfht.demomoviecompose.domain.entity.MovieEntity
import com.dpfht.demomoviecompose.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.demomoviecompose.domain.repository.AppRepository

class AppRepositoryImpl(
  private val localDataSource: LocalDataSource,
  private val remoteDataSource: RemoteDataSource
): AppRepository {

  override suspend fun getPopularMovies(page: Int): MovieDomain {
    return remoteDataSource.getPopularMovies(page)
  }

  override suspend fun getMovieDetails(movieId: Int): MovieEntity {
    return remoteDataSource.getMovieDetails(movieId)
  }

  override suspend fun searchMovie(query: String, page: Int): MovieDomain {
    return remoteDataSource.searchMovie(query, page)
  }

  override suspend fun getAllFavoriteMovies(): List<FavoriteMovieDBEntity> {
    return localDataSource.getAllFavoriteMovies()
  }

  override suspend fun getFavoriteMovie(movieId: Int): FavoriteMovieDBEntity? {
    return localDataSource.getFavoriteMovie(movieId)
  }

  override suspend fun addFavoriteMovie(movie: MovieEntity): FavoriteMovieDBEntity {
    return localDataSource.addFavoriteMovie(movie)
  }

  override suspend fun deleteFavoriteMovie(movie: MovieEntity) {
    return localDataSource.deleteFavoriteMovie(movie)
  }
}
