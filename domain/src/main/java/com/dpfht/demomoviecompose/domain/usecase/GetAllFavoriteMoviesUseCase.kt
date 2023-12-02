package com.dpfht.demomoviecompose.domain.usecase

import com.dpfht.demomoviecompose.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.demomoviecompose.domain.entity.Result
import kotlinx.coroutines.flow.Flow

interface GetAllFavoriteMoviesUseCase {

  suspend operator fun invoke(): Flow<Result<List<FavoriteMovieDBEntity>>>
}
