package com.dpfht.demomoviecompose.domain.usecase

import com.dpfht.demomoviecompose.domain.entity.MovieDomain
import com.dpfht.demomoviecompose.domain.entity.Result
import kotlinx.coroutines.flow.Flow

interface GetPopularMoviesUseCase {

  suspend operator fun invoke(page: Int): Flow<Result<MovieDomain>>
}
