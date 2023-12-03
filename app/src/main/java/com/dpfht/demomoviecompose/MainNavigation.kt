package com.dpfht.demomoviecompose

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dpfht.demomoviecompose.R.string
import com.dpfht.demomoviecompose.feature_error_message.AppBottomSheet
import com.dpfht.demomoviecompose.feature_favorite_movies.FavoriteMoviesScreen
import com.dpfht.demomoviecompose.feature_movie_details.MovieDetailsScreen
import com.dpfht.demomoviecompose.feature_popular_movies.PopularMoviesScreen
import com.dpfht.demomoviecompose.feature_search_movie.SearchMovieScreen
import com.dpfht.demomoviecompose.framework.navigation.NavigationService
import com.dpfht.demomoviecompose.framework.navigation.Screen
import com.dpfht.demomoviecompose.framework.navigation.Screen.MainBaseRoute
import com.dpfht.demomoviecompose.framework.navigation.Screen.None

@Composable
fun MainNavigation(navigationService: NavigationService, navController: NavHostController) {
  var showSheet by remember { mutableStateOf(false) }
  var errorMessage by remember { mutableStateOf("") }

  if (showSheet) {
    AppBottomSheet(
      msg = errorMessage
    ) {
      navigationService.navigate(None)
      showSheet = false
    }
  }

  navigationService.screen.collectAsState().value.also { screen ->
    when (screen) {
      Screen.NavigateUp -> {
        navController.navigateUp()
        navigationService.navigate(None)
      }
      is Screen.NavigateBackWithResult -> {
        navController.previousBackStackEntry?.savedStateHandle?.let {
          if (screen.data != null) {
            screen.data?.let { data ->
              for ((key, value) in data) {
                it[key] = value
              }
            }
          }
        }

        navController.navigateUp()
        navigationService.navigate(None)
      }
      Screen.Home -> {}
      Screen.PopularMovies -> {
        navController.navigate(Screen.PopularMovies.route) {
          popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
          }

          launchSingleTop = true
          restoreState = true
        }
      }
      is Screen.Error -> {
        errorMessage = screen.message
        showSheet = true
        navigationService.navigate(None)
      }
      is Screen.SearchMovie -> {
        navController.navigate(Screen.SearchMovie.route) {
          popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
          }

          launchSingleTop = true
          restoreState = true
        }
      }
      is Screen.FavoriteMovies -> {
        navController.navigate(Screen.FavoriteMovies.route) {
          popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
          }

          launchSingleTop = true
        }
      }
      is Screen.MovieDetails -> {
        navController.navigate(screen.createRoute())
        navigationService.navigate(None)
      }
      else -> {
        if (screen.route.isNotEmpty()) {
          navController.navigate(screen.route)
        }
      }
    }
  }
}

fun NavGraphBuilder.mainGraph(appWindowTitle: String, drawerState: DrawerState, onClickAboutMenuItem: () -> Unit) {
  navigation(route = MainBaseRoute.route, startDestination = Screen.PopularMovies.route) {
    composable(route = Screen.PopularMovies.route) {
      PopularMoviesScreen(drawerState = drawerState, screenTitle = appWindowTitle, onClickAboutMenuItem = onClickAboutMenuItem)
    }
    composable(route = Screen.SearchMovie.route) {
      SearchMovieScreen(drawerState = drawerState, screenTitle = appWindowTitle, onClickAboutMenuItem = onClickAboutMenuItem)
    }
    composable(route = Screen.FavoriteMovies.route) {
      val savedStateHandle = it.savedStateHandle
      FavoriteMoviesScreen(drawerState = drawerState, screenTitle = appWindowTitle, onClickAboutMenuItem = onClickAboutMenuItem, savedStateHandle = savedStateHandle)
    }
    composable(route = Screen.MovieDetails.route) { backStackEntry ->
      val movieId = backStackEntry.arguments?.getString("movieId")
      requireNotNull(movieId) { stringResource(id = string.app_text_movie_id_required) }

      val theMovieId = try {
        movieId.toInt()
      } catch (e: Exception) {
        e.printStackTrace()
        -1
      }

      val isForResult = backStackEntry.arguments?.getString("isForResult")
      requireNotNull(isForResult) { stringResource(id = string.app_text_is_for_result_required) }

      val theForResult = try {
        isForResult.toBoolean()
      } catch (e: Exception) {
        e.printStackTrace()
        false
      }

      MovieDetailsScreen(drawerState = drawerState, appWindowTitle, isForResult = theForResult, movieId = theMovieId, onClickAboutMenuItem = onClickAboutMenuItem)
    }
  }
}
