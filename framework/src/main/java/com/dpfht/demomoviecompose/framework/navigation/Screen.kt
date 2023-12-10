package com.dpfht.demomoviecompose.framework.navigation

sealed class Screen(val route: String) {
  object IntroBaseRoute: Screen("intro")
  object MainBaseRoute: Screen("main")

  object None: Screen("")
  object NavigateUp: Screen("navigateUp")
  data class NavigateBackWithResult(val data: Map<String, Any>?): Screen("navigateBackWithResult")

  object Splash: Screen("splash")
  object Home: Screen("home")
  object PopularMovies: Screen("popular_movies")
  object SearchMovie: Screen("search_movie")
  data class FavoriteMovies(val accessTime: Long): Screen("favorite_movies") {
    companion object {
      const val route = "favorite_movies/{accessTime}"
    }

    fun createRoute() = "favorite_movies/$accessTime"
  }
  data class MovieDetails(val movieId: Int, val isForResult: Boolean) : Screen("") {
    companion object {
      const val route = "movie_details/{movieId}/{isForResult}"
    }

    fun createRoute() = "movie_details/$movieId/$isForResult"
  }
  data class Error(val message: String): Screen("")
}
