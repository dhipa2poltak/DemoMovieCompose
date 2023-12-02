package com.dpfht.demomoviecompose.domain.usecase

import com.dpfht.demomoviecompose.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.demomoviecompose.domain.entity.Result
import kotlinx.coroutines.flow.Flow

interface GetFavoriteMovieUseCase {

  suspend operator fun invoke(movieId: Int): Flow<Result<FavoriteMovieDBEntity?>>
}
