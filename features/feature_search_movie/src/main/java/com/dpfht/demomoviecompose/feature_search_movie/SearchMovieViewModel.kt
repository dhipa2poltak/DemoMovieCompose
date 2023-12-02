package com.dpfht.demomoviecompose.feature_search_movie

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dpfht.demomoviecompose.feature_search_movie.event_state.UIEvent
import com.dpfht.demomoviecompose.feature_search_movie.event_state.UIState
import com.dpfht.demomoviecompose.feature_search_movie.paging.SearchMovieDataSource
import com.dpfht.demomoviecompose.framework.navigation.NavigationService
import com.dpfht.demomoviecompose.framework.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
  private val navigationService: NavigationService,
  private val searchMovieDataSource: SearchMovieDataSource,
): ViewModel() {

  private var _uiState = mutableStateOf(UIState())
  val uiState: State<UIState> = _uiState

  fun onEvent(event: UIEvent) {
    when (event) {
      is UIEvent.QueryTextChanged -> {
        _uiState.value = _uiState.value.copy(queryText = event.queryText, hasQueryTextError = false)
      }
      UIEvent.OnSearch -> {
        if (_uiState.value.queryText.trim().isEmpty()) {
          _uiState.value = _uiState.value.copy(hasQueryTextError = true)
        } else {
          _uiState.value = _uiState.value.copy(hasQueryTextError = false)
          searchMovie(_uiState.value.queryText)
        }
      }
      is UIEvent.OnClickMovieItem -> {
        navigationService.navigate(Screen.MovieDetails(event.movieId, false))
      }
      UIEvent.OnBackPressed -> {
        navigationService.navigate(Screen.PopularMovies)
      }
      is UIEvent.OnError -> {
        showErrorMessage(event.message)
      }
    }
  }

  private fun searchMovie(queryText: String) {
    searchMovieDataSource.query = queryText
    searchMovieDataSource.uiState = _uiState

    val pager = Pager(PagingConfig(pageSize = 20)) {
      searchMovieDataSource
    }.flow.cachedIn(viewModelScope)

    _uiState.value = _uiState.value.copy(moviesState = MutableStateFlow(value = PagingData.empty()))

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
