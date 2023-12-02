package com.dpfht.demomoviecompose.domain.usecase

import com.dpfht.demomoviecompose.domain.entity.MovieEntity
import com.dpfht.demomoviecompose.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.demomoviecompose.domain.entity.Result
import com.dpfht.demomoviecompose.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddFavoriteMovieUseCaseImpl(
  private val appRepository: AppRepository
): AddFavoriteMovieUseCase {

  override suspend operator fun invoke(movie: MovieEntity): Flow<Result<FavoriteMovieDBEntity>> = flow {
    try {
      emit(Result.Success(appRepository.addFavoriteMovie(movie)))
    } catch (e: Exception) {
      e.message?.let {
        emit(Result.Error(it))
      }
    }
  }
}
