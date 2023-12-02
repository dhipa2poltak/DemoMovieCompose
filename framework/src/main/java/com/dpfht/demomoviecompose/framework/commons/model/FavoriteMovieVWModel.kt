package com.dpfht.demomoviecompose.framework.commons.model

import com.dpfht.demomoviecompose.domain.entity.MovieEntity
import com.dpfht.demomoviecompose.domain.entity.db_entity.FavoriteMovieDBEntity

data class FavoriteMovieVWModel(
  val favEntity: FavoriteMovieDBEntity,
  var movieEntity: MovieEntity? = null
)
