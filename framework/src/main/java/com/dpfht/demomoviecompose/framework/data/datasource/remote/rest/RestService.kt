package com.dpfht.demomoviecompose.framework.data.datasource.remote.rest

import com.dpfht.demomoviecompose.data.model.MovieDetailsResponse
import com.dpfht.demomoviecompose.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestService {

  @GET("3/movie/popular")
  suspend fun getPopularMovies(
    @Query("page") page: Int,
    @Query("language") language: String = "en-US"
  ): MovieResponse

  @GET("3/search/movie")
  suspend fun searchMovie(
    @Query("query") query: String,
    @Query("page") page: Int,
    @Query("language") language: String = "en-US"
  ): MovieResponse

  @GET("3/movie/{movie_id}")
  suspend fun getMovieDetails(
    @Path("movie_id") movieId: Int
  ): MovieDetailsResponse
}
