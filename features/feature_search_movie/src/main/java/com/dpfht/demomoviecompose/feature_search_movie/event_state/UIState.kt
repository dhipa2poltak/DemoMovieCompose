package com.dpfht.demomoviecompose.feature_search_movie.event_state

import androidx.paging.PagingData
import com.dpfht.demomoviecompose.domain.entity.MovieEntity
import kotlinx.coroutines.flow.MutableStateFlow

data class UIState(
  val queryText: String = "",
  val isLoaded: Boolean = false,
  val isLoading: Boolean = false,
  val moviesState: MutableStateFlow<PagingData<MovieEntity>> = MutableStateFlow(value = PagingData.empty()),
  val errorMessage: String = "",
  val hasQueryTextError: Boolean = false
)
