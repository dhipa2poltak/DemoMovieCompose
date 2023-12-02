package com.dpfht.demomoviecompose.data.datasource

import com.dpfht.demomoviecompose.domain.entity.MovieDomain
import com.dpfht.demomoviecompose.domain.entity.MovieEntity

interface RemoteDataSource {

  suspend fun getPopularMovies(page: Int): MovieDomain
  suspend fun getMovieDetails(movieId: Int): MovieEntity
  suspend fun searchMovie(query: String, page: Int): MovieDomain
}
