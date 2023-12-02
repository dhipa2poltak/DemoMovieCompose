package com.dpfht.demomoviecompose.feature_popular_movies.event_state

sealed class UIEvent {
  object Init: UIEvent()
  data class OnClickMovieItem(val movieId: Int): UIEvent()
  object OnBackPressed: UIEvent()
  data class OnError(val message: String): UIEvent()
}
