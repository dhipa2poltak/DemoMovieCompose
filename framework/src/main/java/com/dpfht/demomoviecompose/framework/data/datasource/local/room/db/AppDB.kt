package com.dpfht.demomoviecompose.framework.data.datasource.local.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dpfht.demomoviecompose.framework.data.datasource.local.room.dao.FavoriteMovieDao
import com.dpfht.demomoviecompose.framework.data.datasource.local.room.model.FavoriteMovieDBModel

@Database(version = 1, entities = [FavoriteMovieDBModel::class], exportSchema = false)
abstract class AppDB: RoomDatabase() {

  abstract fun favoriteMovieDao(): FavoriteMovieDao

  companion object {
    private var INSTANCE: AppDB? = null

    fun getDatabase(context: Context): AppDB {
      val tempInstance = INSTANCE
      if (tempInstance != null) {
        return tempInstance
      }

      synchronized(this) {
        val instance = Room.databaseBuilder(context.applicationContext,
          AppDB::class.java,
          "movie_compose_database")
          .build()
        INSTANCE = instance
        return instance
      }
    }
  }
}
