package com.dpfht.demomoviecompose.feature_search_movie.event_state

sealed class UIEvent {
  data class QueryTextChanged(val queryText: String): UIEvent()
  object OnSearch: UIEvent()
  data class OnClickMovieItem(val movieId: Int): UIEvent()
  object OnBackPressed: UIEvent()
  data class OnError(val message: String): UIEvent()
}
