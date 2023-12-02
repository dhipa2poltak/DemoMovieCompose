package com.dpfht.demomoviecompose.framework.data.datasource.local

import android.content.Context
import com.dpfht.demomoviecompose.data.datasource.LocalDataSource
import com.dpfht.demomoviecompose.domain.entity.MovieEntity
import com.dpfht.demomoviecompose.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.demomoviecompose.framework.R
import com.dpfht.demomoviecompose.framework.data.datasource.local.room.db.AppDB
import com.dpfht.demomoviecompose.framework.data.datasource.local.room.model.FavoriteMovieDBModel
import com.dpfht.demomoviecompose.framework.data.datasource.local.room.model.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSourceImpl(
  private val context: Context,
  private val appDB: AppDB
): LocalDataSource {

  override suspend fun getAllFavoriteMovies(): List<FavoriteMovieDBEntity> {
    try {
      return withContext(Dispatchers.IO) { appDB.favoriteMovieDao().getAllFavoriteMovies().map { it.toDomain() }}
    } catch (e: Exception) {
      e.printStackTrace()
      throw Exception(context.getString(R.string.framework_text_fail_get_all_favorite_movies))
    }
  }

  override suspend fun getFavoriteMovie(movieId: Int): FavoriteMovieDBEntity? {
    try {
      val list = withContext(Dispatchers.IO) { appDB.favoriteMovieDao().getFavoriteMovie(movieId) }

      return if (list.isNotEmpty()) {
        list.first().toDomain()
      } else {
        null
      }
    } catch (e: Exception) {
      e.printStackTrace()
      throw Exception(context.getString(R.string.framework_text_fail_get_favorite_movie))
    }
  }

  override suspend fun addFavoriteMovie(movie: MovieEntity): FavoriteMovieDBEntity {
    try {
      val dbEntity = withContext(Dispatchers.IO) {
        val dbModel = FavoriteMovieDBModel(movieId = movie.id)
        val newId = appDB.favoriteMovieDao().insertFavoriteMovie(dbModel)

        FavoriteMovieDBEntity(
          id = newId,
          movieId = movie.id
        )
      }

      return dbEntity
    } catch (e: Exception) {
      e.printStackTrace()
      throw Exception(context.getString(R.string.framework_text_fail_add_favorite_movie))
    }
  }

  override suspend fun deleteFavoriteMovie(movie: MovieEntity) {
    try {
      appDB.favoriteMovieDao().deleteFavoriteMovie(movie.id)
    } catch (e: Exception) {
      e.printStackTrace()
      throw Exception(context.getString(R.string.framework_text_fail_delete_favorite_movie))
    }
  }
}
