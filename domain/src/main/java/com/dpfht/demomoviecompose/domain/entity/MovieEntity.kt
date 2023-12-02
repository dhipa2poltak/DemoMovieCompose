package com.dpfht.demomoviecompose.domain.entity

data class MovieEntity(
  val id: Int = -1,
  val title: String = "",
  val overview: String = "",
  val imageUrl: String = "",
  val genres: List<GenreEntity> = listOf()
)
