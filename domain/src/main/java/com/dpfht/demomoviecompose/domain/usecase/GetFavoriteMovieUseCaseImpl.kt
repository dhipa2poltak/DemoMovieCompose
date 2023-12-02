package com.dpfht.demomoviecompose.domain.usecase

import com.dpfht.demomoviecompose.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.demomoviecompose.domain.entity.Result
import com.dpfht.demomoviecompose.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetFavoriteMovieUseCaseImpl(
  private val appRepository: AppRepository
): GetFavoriteMovieUseCase {

  override suspend operator fun invoke(movieId: Int): Flow<Result<FavoriteMovieDBEntity?>> = flow {
    try {
      emit(Result.Success(appRepository.getFavoriteMovie(movieId)))
    } catch (e: Exception) {
      e.message?.let {
        emit(Result.Error(it))
      }
    }
  }
}
