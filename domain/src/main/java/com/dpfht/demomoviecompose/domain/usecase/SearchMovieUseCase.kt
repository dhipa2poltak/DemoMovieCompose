package com.dpfht.demomoviecompose.domain.usecase

import com.dpfht.demomoviecompose.domain.entity.MovieDomain
import com.dpfht.demomoviecompose.domain.entity.Result
import kotlinx.coroutines.flow.Flow

interface SearchMovieUseCase {

  suspend operator fun invoke(query: String, page: Int): Flow<Result<MovieDomain>>
}
