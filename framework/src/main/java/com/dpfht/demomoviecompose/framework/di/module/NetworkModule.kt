package com.dpfht.demomoviecompose.framework.di.module

import android.content.Context
import com.dpfht.demomoviecompose.data.datasource.RemoteDataSource
import com.dpfht.demomoviecompose.framework.BuildConfig
import com.dpfht.demomoviecompose.framework.Constants
import com.dpfht.demomoviecompose.framework.data.datasource.remote.RemoteDataSourceImpl
import com.dpfht.demomoviecompose.framework.data.datasource.remote.rest.AuthInterceptor
import com.dpfht.demomoviecompose.framework.data.datasource.remote.rest.RestService
import com.dpfht.demomoviecompose.framework.data.datasource.remote.rest.UnsafeOkHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

  @Provides
  @Singleton
  fun provideCertificatePinner(): CertificatePinner {
    return CertificatePinner.Builder()
      .add("api.themoviedb.org", "sha256/oD/WAoRPvbez1Y2dfYfuo4yujAcYHXdv1Ivb2v2MOKk=")
      .add("api.themoviedb.org", "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
      .add("api.themoviedb.org", "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
      .add("api.themoviedb.org", "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
      .build()
  }

  @Provides
  @Singleton
  fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return httpLoggingInterceptor
  }

  @Provides
  @Singleton
  fun provideAuthInterceptor(): AuthInterceptor {
    return AuthInterceptor()
  }

  @Provides
  @Singleton
  fun provideClient(certificatePinner: CertificatePinner, authInterceptor: AuthInterceptor): OkHttpClient {
    if (BuildConfig.DEBUG) {
      return UnsafeOkHttpClient.getUnsafeOkHttpClient()
    }

    val httpClientBuilder = OkHttpClient()
      .newBuilder()
      .certificatePinner(certificatePinner)

    httpClientBuilder.addInterceptor(authInterceptor)

    return httpClientBuilder.build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .addConverterFactory(GsonConverterFactory.create())
      .baseUrl(Constants.BASE_URL)
      .client(okHttpClient)
      .build()
  }

  @Provides
  @Singleton
  fun provideApiService(retrofit: Retrofit): RestService {
    return retrofit.create(RestService::class.java)
  }

  @Provides
  @Singleton
  fun provideRemoteDataSource(@ApplicationContext context: Context, restService: RestService): RemoteDataSource {
    return RemoteDataSourceImpl(context, restService)
  }
}
