package com.dpfht.demomoviecompose.feature_favorite_movies.event_state

import com.dpfht.demomoviecompose.framework.commons.model.FavoriteMovieVWModel

sealed class UIEvent {
  object Init: UIEvent()
  data class FetchMovieDetails(val favModel: FavoriteMovieVWModel): UIEvent()
  data class OnClickMovieItem(val movieId: Int): UIEvent()
  data class OnDeleteMovieItem(val movieId: Int): UIEvent()
  object OnBackPressed: UIEvent()
}
