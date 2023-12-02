package com.dpfht.demomoviecompose.feature_favorite_movies.event_state

data class UIState(
  val isLoaded: Boolean = false,
  val isLoading: Boolean = false,
  val errorMessage: String = ""
)
