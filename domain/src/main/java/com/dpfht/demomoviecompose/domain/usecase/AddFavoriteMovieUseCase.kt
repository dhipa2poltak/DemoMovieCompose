package com.dpfht.demomoviecompose.domain.usecase

import com.dpfht.demomoviecompose.domain.entity.MovieEntity
import com.dpfht.demomoviecompose.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.demomoviecompose.domain.entity.Result
import kotlinx.coroutines.flow.Flow

interface AddFavoriteMovieUseCase {

  suspend operator fun invoke(movie: MovieEntity): Flow<Result<FavoriteMovieDBEntity>>
}
