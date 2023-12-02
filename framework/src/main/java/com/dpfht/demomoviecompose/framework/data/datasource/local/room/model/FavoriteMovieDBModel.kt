package com.dpfht.demomoviecompose.framework.data.datasource.local.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dpfht.demomoviecompose.domain.entity.db_entity.FavoriteMovieDBEntity

@Entity(tableName = "tbl_favorite_movies", indices = [Index(value = ["movie_id"], unique = true)])
data class FavoriteMovieDBModel(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo("id")
  val id: Long? = null,
  @ColumnInfo(name = "movie_id")
  val movieId: Int? = 0,
)

fun FavoriteMovieDBModel.toDomain(): FavoriteMovieDBEntity {
  return FavoriteMovieDBEntity(
    id = this.id ?: 0,
    movieId = this.movieId ?: 0
  )
}
