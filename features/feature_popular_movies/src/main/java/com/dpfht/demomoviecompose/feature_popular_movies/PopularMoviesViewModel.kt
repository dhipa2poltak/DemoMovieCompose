package com.dpfht.demomoviecompose.feature_popular_movies

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.dpfht.demomoviecompose.feature_popular_movies.event_state.UIEvent
import com.dpfht.demomoviecompose.feature_popular_movies.event_state.UIState
import com.dpfht.demomoviecompose.feature_popular_movies.paging.PopularMoviesDataSource
import com.dpfht.demomoviecompose.framework.navigation.NavigationService
import com.dpfht.demomoviecompose.framework.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
  private val navigationService: NavigationService,
  private val popularMoviesDataSource: PopularMoviesDataSource,
): ViewModel() {

  private var _uiState = mutableStateOf(UIState())
  val uiState: State<UIState> = _uiState

  fun onEvent(event: UIEvent) {
    when (event) {
      UIEvent.Init -> {
        start()
      }
      is UIEvent.OnClickMovieItem -> {
        navigationService.navigate(Screen.MovieDetails(event.movieId, false))
      }
      UIEvent.OnBackPressed -> {
        navigationService.navigateUp()
      }
      is UIEvent.OnError -> {
        showErrorMessage(event.message)
      }
    }
  }

  private fun start() {
    if (!_uiState.value.isLoaded) {
      loadPopularMovies()
    }
  }

  private fun loadPopularMovies() {
    popularMoviesDataSource.uiState = _uiState

    val pager = Pager(PagingConfig(pageSize = 20)) {
      popularMoviesDataSource
    }.flow.cachedIn(viewModelScope)

    viewModelScope.launch {
      pager.collect {
        _uiState.value = _uiState.value.copy(isLoaded = true)
        _uiState.value.moviesState.value = it
      }
    }
  }

  private fun showErrorMessage(message: String) {
    navigationService.navigateToErrorMessage(message)
  }
}
