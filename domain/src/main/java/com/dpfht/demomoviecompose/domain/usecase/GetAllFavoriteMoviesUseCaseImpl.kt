package com.dpfht.demomoviecompose.domain.usecase

import com.dpfht.demomoviecompose.domain.entity.db_entity.FavoriteMovieDBEntity
import com.dpfht.demomoviecompose.domain.entity.Result
import com.dpfht.demomoviecompose.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllFavoriteMoviesUseCaseImpl(
  private val appRepository: AppRepository
): GetAllFavoriteMoviesUseCase {

  override suspend operator fun invoke(): Flow<Result<List<FavoriteMovieDBEntity>>> = flow {
    try {
      emit(Result.Success(appRepository.getAllFavoriteMovies()))
    } catch (e: Exception) {
      e.message?.let {
        emit(Result.Error(it))
      }
    }
  }
}
