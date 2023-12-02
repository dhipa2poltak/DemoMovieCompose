package com.dpfht.demomoviecompose.feature_movie_details.event_state

import com.dpfht.demomoviecompose.domain.entity.GenreEntity

data class UIState(
  val isForResult: Boolean = false,
  val movieId: Int = -1,
  val isLoaded: Boolean = false,
  val isLoading: Boolean = false,
  val errorMessage: String = "",
  val title: String = "",
  val imageUrl: String = "",
  val description: String = "",
  val isFavorite: Boolean = false,
  val genres: List<GenreEntity> = listOf()
)
