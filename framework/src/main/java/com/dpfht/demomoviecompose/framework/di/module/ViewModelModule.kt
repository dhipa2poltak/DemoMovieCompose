package com.dpfht.demomoviecompose.framework.di.module

import com.dpfht.demomoviecompose.domain.repository.AppRepository
import com.dpfht.demomoviecompose.domain.usecase.AddFavoriteMovieUseCase
import com.dpfht.demomoviecompose.domain.usecase.AddFavoriteMovieUseCaseImpl
import com.dpfht.demomoviecompose.domain.usecase.DeleteFavoriteMovieUseCase
import com.dpfht.demomoviecompose.domain.usecase.DeleteFavoriteMovieUseCaseImpl
import com.dpfht.demomoviecompose.domain.usecase.GetAllFavoriteMoviesUseCase
import com.dpfht.demomoviecompose.domain.usecase.GetAllFavoriteMoviesUseCaseImpl
import com.dpfht.demomoviecompose.domain.usecase.GetFavoriteMovieUseCase
import com.dpfht.demomoviecompose.domain.usecase.GetFavoriteMovieUseCaseImpl
import com.dpfht.demomoviecompose.domain.usecase.GetMovieDetailsUseCase
import com.dpfht.demomoviecompose.domain.usecase.GetMovieDetailsUseCaseImpl
import com.dpfht.demomoviecompose.domain.usecase.GetPopularMoviesUseCase
import com.dpfht.demomoviecompose.domain.usecase.GetPopularMoviesUseCaseImpl
import com.dpfht.demomoviecompose.domain.usecase.SearchMovieUseCase
import com.dpfht.demomoviecompose.domain.usecase.SearchMovieUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

  @Provides
  fun provideGetMovieDetailsUseCase(appRepository: AppRepository): GetMovieDetailsUseCase {
    return GetMovieDetailsUseCaseImpl(appRepository)
  }

  @Provides
  fun provideGetPopularMoviesUseCase(appRepository: AppRepository): GetPopularMoviesUseCase {
    return GetPopularMoviesUseCaseImpl(appRepository)
  }

  @Provides
  fun provideSearchMovieUseCase(appRepository: AppRepository): SearchMovieUseCase {
    return SearchMovieUseCaseImpl(appRepository)
  }

  @Provides
  fun provideGetAllFavoriteMoviesUseCase(appRepository: AppRepository): GetAllFavoriteMoviesUseCase {
    return GetAllFavoriteMoviesUseCaseImpl(appRepository)
  }

  @Provides
  fun provideGetFavoriteMovieUseCase(appRepository: AppRepository): GetFavoriteMovieUseCase {
    return GetFavoriteMovieUseCaseImpl(appRepository)
  }

  @Provides
  fun provideAddFavoriteMovieUseCase(appRepository: AppRepository): AddFavoriteMovieUseCase {
    return AddFavoriteMovieUseCaseImpl(appRepository)
  }

  @Provides
  fun provideDeleteFavoriteMovieUseCase(appRepository: AppRepository): DeleteFavoriteMovieUseCase {
    return DeleteFavoriteMovieUseCaseImpl(appRepository)
  }

  /*
  @Provides
  fun provideArrayListFavoriteMovieCacheModel(): ArrayList<FavoriteMovieVWModel> {
    return arrayListOf()
  }
  */
}
