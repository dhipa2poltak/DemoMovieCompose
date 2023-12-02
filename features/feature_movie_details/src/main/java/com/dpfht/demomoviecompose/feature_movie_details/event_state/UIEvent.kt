package com.dpfht.demomoviecompose.feature_movie_details.event_state

sealed class UIEvent {
  data class Init(val movieId: Int, val isForResult: Boolean): UIEvent()
  object OnClickAddToFavorite: UIEvent()
  object OnClickDeleteFromFavorite: UIEvent()
  object OnBackPressed: UIEvent()
}
