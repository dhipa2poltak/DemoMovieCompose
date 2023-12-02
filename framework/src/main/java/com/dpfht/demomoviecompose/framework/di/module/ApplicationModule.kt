package com.dpfht.demomoviecompose.framework.di.module

import com.dpfht.demomoviecompose.data.datasource.LocalDataSource
import com.dpfht.demomoviecompose.data.datasource.RemoteDataSource
import com.dpfht.demomoviecompose.data.repository.AppRepositoryImpl
import com.dpfht.demomoviecompose.domain.repository.AppRepository
import com.dpfht.demomoviecompose.framework.navigation.NavigationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

  @Singleton
  @Provides
  fun providesNavigationService() = NavigationService()

  @Provides
  @Singleton
  fun provideAppRepository(localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource): AppRepository {
    return AppRepositoryImpl(localDataSource, remoteDataSource)
  }
}
