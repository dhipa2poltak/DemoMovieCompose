package com.dpfht.demomoviecompose.domain.usecase

import com.dpfht.demomoviecompose.domain.entity.MovieEntity
import com.dpfht.demomoviecompose.domain.entity.VoidResult
import kotlinx.coroutines.flow.Flow

interface DeleteFavoriteMovieUseCase {

  suspend operator fun invoke(movie: MovieEntity): Flow<VoidResult>
}
