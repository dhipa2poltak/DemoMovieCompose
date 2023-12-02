package com.dpfht.demomoviecompose.data.model

import androidx.annotation.Keep
import com.dpfht.demomoviecompose.data.Constants
import com.dpfht.demomoviecompose.domain.entity.MovieDomain
import com.dpfht.demomoviecompose.domain.entity.MovieEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
@Suppress("unused")
data class MovieResponse(
  val page: Int? = -1,
  val results: List<Movie>? = listOf(),

  @SerializedName("total_pages")
  @Expose
  val totalPages: Int? = -1,

  @SerializedName("total_results")
  @Expose
  val totalResults: Int? = -1
)

fun MovieResponse.toDomain(): MovieDomain {
  val movieEntities = results?.map {
    MovieEntity(
      it.id ?: -1,
      it.title ?: "",
      it.overview ?: "",
      if (it.posterPath?.isNotEmpty() == true) Constants.IMAGE_URL_BASE_PATH + it.posterPath else ""
    )
  }

  return MovieDomain(
    page ?: -1,
    movieEntities?.toList() ?: listOf(),
    totalPages ?: -1,
    totalResults ?: -1
  )
}
