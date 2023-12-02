package com.dpfht.demomoviecompose.feature_favorite_movies

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpfht.demomoviecompose.domain.entity.Result.Error
import com.dpfht.demomoviecompose.domain.entity.Result.Success
import com.dpfht.demomoviecompose.domain.entity.VoidResult
import com.dpfht.demomoviecompose.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.demomoviecompose.domain.usecase.DeleteFavoriteMovieUseCase
import com.dpfht.demomoviecompose.domain.usecase.GetAllFavoriteMoviesUseCase
import com.dpfht.demomoviecompose.domain.usecase.GetMovieDetailsUseCase
import com.dpfht.demomoviecompose.feature_favorite_movies.event_state.UIEvent
import com.dpfht.demomoviecompose.feature_favorite_movies.event_state.UIState
import com.dpfht.demomoviecompose.framework.commons.model.FavoriteMovieVWModel
import com.dpfht.demomoviecompose.framework.navigation.NavigationService
import com.dpfht.demomoviecompose.framework.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
  private val navigationService: NavigationService,
  private val getAllFavoriteMoviesUseCase: GetAllFavoriteMoviesUseCase,
  private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
  private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
): ViewModel() {

  private var _uiState = mutableStateOf(UIState())
  val uiState: State<UIState> = _uiState

  private val _itemsState = mutableStateListOf<FavoriteMovieVWModel>()
  val itemsState: List<FavoriteMovieVWModel> = _itemsState

  fun onEvent(event: UIEvent) {
    when (event) {
      UIEvent.Init -> {
        start()
      }
      is UIEvent.FetchMovieDetails -> {
        fetchMovieDetails(event.favModel)
      }
      is UIEvent.OnClickMovieItem -> {
        navigationService.navigate(Screen.MovieDetails(event.movieId, true))
      }
      is UIEvent.OnDeleteMovieItem -> {
        deleteFavoriteMovie(event.movieId)
      }
      UIEvent.OnBackPressed -> {
        navigationService.navigate(Screen.PopularMovies)
      }
    }
  }

  private fun start() {
    if (_uiState.value.isLoaded) {
      return
    }

    _uiState.value = _uiState.value.copy(isLoading = true)
    viewModelScope.launch {
      getAllFavoriteMoviesUseCase().collect { result ->
        when (result) {
          is Success -> {
            onSuccessGetAllFavoriteMovies(result.value)
          }
          is Error -> {
            onErrorGetAllFavoriteMovies(result.message)
          }
        }
      }
    }
  }

  private fun onSuccessGetAllFavoriteMovies(list: List<FavoriteMovieDBEntity>) {
    _uiState.value = _uiState.value.copy(
      isLoaded = true,
      isLoading = false
    )
    _itemsState.addAll(list.map { FavoriteMovieVWModel(it, null) })
  }

  private fun onErrorGetAllFavoriteMovies(message: String) {
    _uiState.value = _uiState.value.copy(
      isLoading = false,
      errorMessage = message
    )
    navigationService.navigateToErrorMessage(message)
  }

  private fun fetchMovieDetails(favMovie: FavoriteMovieVWModel) {
    viewModelScope.launch {
      getMovieDetailsUseCase(favMovie.favEntity.movieId).collect { result ->
        when (result) {
          is Success -> {
            val idx = _itemsState.indexOf(favMovie)
            if (idx != -1) {
              _itemsState[idx] = favMovie.copy(movieEntity = result.value)
            }
          }
          is Error -> {
            favMovie.movieEntity = null
          }
        }
      }
    }
  }

  private fun deleteFavoriteMovie(movieId: Int) {
    _uiState.value = _uiState.value.copy(isLoading = true)

    val favMovie = _itemsState.first { it.favEntity.movieId == movieId }
    val movieEntity = favMovie.movieEntity

    movieEntity?.let {
      viewModelScope.launch {
        deleteFavoriteMovieUseCase(movieEntity).collect { voidResult ->
          when (voidResult) {
            VoidResult.Success -> {
              onSuccessDeleteFavoriteMovie(movieId)
            }
            is VoidResult.Error -> {
              onErrorDeleteFavoriteMovie(voidResult.message)
            }
          }
        }
      }
    }
  }

  private fun onSuccessDeleteFavoriteMovie(movieId: Int) {
    _uiState.value = _uiState.value.copy(isLoading = false)

    val favMovie = _itemsState.first { it.favEntity.movieId == movieId }
    val position = _itemsState.indexOf(favMovie)
    if (position != -1) {
      _itemsState.removeAt(position)
    }
  }

  private fun onErrorDeleteFavoriteMovie(message: String) {
    _uiState.value = _uiState.value.copy(
      isLoading = false,
      errorMessage = message
    )
    navigationService.navigateToErrorMessage(message)
  }
}
