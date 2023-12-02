package com.dpfht.demomoviecompose.domain.usecase

import com.dpfht.demomoviecompose.domain.entity.MovieEntity
import com.dpfht.demomoviecompose.domain.entity.Result
import kotlinx.coroutines.flow.Flow

interface GetMovieDetailsUseCase {

  suspend operator fun invoke(movieId: Int): Flow<Result<MovieEntity>>
}
