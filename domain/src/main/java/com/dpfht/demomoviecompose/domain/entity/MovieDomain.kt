package com.dpfht.demomoviecompose.domain.entity

data class MovieDomain(
  val page: Int = 0,
  val results: List<MovieEntity> = listOf(),
  val totalPages: Int = 0,
  val totalResults: Int = 0
)
