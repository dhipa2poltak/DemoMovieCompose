package com.dpfht.demomoviecompose.feature_popular_movies.event_state

import androidx.paging.PagingData
import com.dpfht.demomoviecompose.domain.entity.MovieEntity
import kotlinx.coroutines.flow.MutableStateFlow

data class UIState(
  val isLoaded: Boolean = false,
  val isLoading: Boolean = false,
  val moviesState: MutableStateFlow<PagingData<MovieEntity>> = MutableStateFlow(value = PagingData.empty()),
  val errorMessage: String = ""
)
