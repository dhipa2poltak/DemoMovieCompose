package com.dpfht.demomoviecompose.domain.usecase

import com.dpfht.demomoviecompose.domain.entity.MovieEntity
import com.dpfht.demomoviecompose.domain.entity.Result
import com.dpfht.demomoviecompose.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovieDetailsUseCaseImpl(
  private val appRepository: AppRepository
): GetMovieDetailsUseCase {

  override suspend operator fun invoke(movieId: Int): Flow<Result<MovieEntity>> = flow {
    try {
      emit(Result.Success(appRepository.getMovieDetails(movieId)))
    } catch (e: Exception) {
      e.message?.let {
        emit(Result.Error(it))
      }
    }
  }
}
