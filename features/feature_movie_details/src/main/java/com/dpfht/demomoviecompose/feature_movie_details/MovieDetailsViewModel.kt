package com.dpfht.demomoviecompose.feature_movie_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpfht.demomoviecompose.domain.entity.MovieEntity
import com.dpfht.demomoviecompose.domain.entity.Result
import com.dpfht.demomoviecompose.domain.entity.VoidResult
import com.dpfht.demomoviecompose.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.demomoviecompose.domain.usecase.AddFavoriteMovieUseCase
import com.dpfht.demomoviecompose.domain.usecase.DeleteFavoriteMovieUseCase
import com.dpfht.demomoviecompose.domain.usecase.GetFavoriteMovieUseCase
import com.dpfht.demomoviecompose.domain.usecase.GetMovieDetailsUseCase
import com.dpfht.demomoviecompose.feature_movie_details.event_state.UIEvent
import com.dpfht.demomoviecompose.feature_movie_details.event_state.UIState
import com.dpfht.demomoviecompose.framework.navigation.NavigationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
  private val navigationService: NavigationService,
  private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
  private val getFavoriteMovieUseCase: GetFavoriteMovieUseCase,
  private val addFavoriteMovieUseCase: AddFavoriteMovieUseCase,
  private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase
): ViewModel() {

  private var _uiState = mutableStateOf(UIState())
  val uiState: State<UIState> = _uiState

  private var movieEntity: MovieEntity? = null

  fun onEvent(event: UIEvent) {
    when (event) {
      is UIEvent.Init -> {
        _uiState.value = _uiState.value.copy(isForResult = event.isForResult, movieId = event.movieId)
        start()
      }
      UIEvent.OnClickAddToFavorite -> {
        addFavoriteMovie()
      }
      UIEvent.OnClickDeleteFromFavorite -> {
        deleteFavoriteMovie()
      }
      UIEvent.OnBackPressed -> {
        if (_uiState.value.isForResult) {
          val data = mapOf(
           "movieId" to _uiState.value.movieId,
           "isFavorite" to _uiState.value.isFavorite
          )

          navigationService.navigateBackForResult(data)
        } else {
          navigationService.navigateUp()
        }
      }
    }
  }

  private fun start() {
    if (_uiState.value.movieId == -1 || _uiState.value.isLoaded) {
      return
    }

    _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = "")
    viewModelScope.launch {
      getMovieDetailsUseCase(_uiState.value.movieId).collect { result ->
        when (result) {
          is Result.Success -> {
            onSuccessGetMovieDetails(result.value)
            checkIsFavorite(result.value)
          }
          is Result.Error -> {
            onErrorGetMovieDetails(result.message)
          }
        }
      }
    }
  }

  private fun onSuccessGetMovieDetails(movieEntity: MovieEntity) {
    this.movieEntity = movieEntity
    _uiState.value = _uiState.value.copy(
      isLoaded = true,
      title = movieEntity.title,
      imageUrl = movieEntity.imageUrl,
      description = movieEntity.overview,
      genres = movieEntity.genres
    )
  }

  private fun onErrorGetMovieDetails(message: String) {
    _uiState.value = _uiState.value.copy(
      isLoading = false,
      errorMessage = message
    )
    navigationService.navigateToErrorMessage(message)
  }

  private fun checkIsFavorite(movieEntity: MovieEntity) {
    viewModelScope.launch {
      getFavoriteMovieUseCase(movieEntity.id).collect { result ->
        when (result) {
          is Result.Success -> {
            onSuccessCheckIsFavorite(result.value)
          }
          is Result.Error -> {
            onErrorCheckIsFavorite(result.message)
          }
        }
      }
    }
  }

  private fun onSuccessCheckIsFavorite(entity: FavoriteMovieDBEntity?) {
    _uiState.value = _uiState.value.copy(
      isLoading = false,
      isFavorite = entity != null
    )
  }

  private fun onErrorCheckIsFavorite(message: String) {
    _uiState.value = _uiState.value.copy(
      isLoading = false,
      errorMessage = message
    )
    navigationService.navigateToErrorMessage(message)
  }

  private fun addFavoriteMovie() {
    if (_uiState.value.isForResult) {
      _uiState.value = _uiState.value.copy(isFavorite = true)
      return
    }

    _uiState.value = _uiState.value.copy(
      isLoading = true
    )

    viewModelScope.launch {
      movieEntity?.let {
        addFavoriteMovieUseCase(it).collect { result ->
          when (result) {
            is Result.Success -> {
              onSuccessAddFavoriteMovie(result.value)
            }
            is Result.Error -> {
              onErrorAddFavoriteMovie(result.message)
            }
          }
        }
      }
    }
  }

  private fun onSuccessAddFavoriteMovie(entity: FavoriteMovieDBEntity) {
    _uiState.value = _uiState.value.copy(
      isLoading = false,
      isFavorite = entity.id > 0
    )
  }

  private fun onErrorAddFavoriteMovie(message: String) {
    _uiState.value = _uiState.value.copy(
      isLoading = false,
      errorMessage = message
    )
    navigationService.navigateToErrorMessage(message)
  }

  private fun deleteFavoriteMovie() {
    if (_uiState.value.isForResult) {
      _uiState.value = _uiState.value.copy(isFavorite = false)
      return
    }

    _uiState.value = _uiState.value.copy(
      isLoading = true
    )

    viewModelScope.launch {
      movieEntity?.let {
        deleteFavoriteMovieUseCase(it).collect { voidResult ->
          when (voidResult) {
            VoidResult.Success -> {
              onSuccessDeleteFavoriteMovie()
            }
            is VoidResult.Error -> {
              onErrorDeleteFavoriteMovie(voidResult.message)
            }
          }
        }
      }
    }
  }

  private fun onSuccessDeleteFavoriteMovie() {
    _uiState.value = _uiState.value.copy(
      isLoading = false,
      isFavorite = false
    )
  }

  private fun onErrorDeleteFavoriteMovie(message: String) {
    _uiState.value = _uiState.value.copy(
      isLoading = false,
      errorMessage = message
    )
    navigationService.navigateToErrorMessage(message)
  }
}
