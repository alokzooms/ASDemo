package com.maxlabs.asdemo.presenter.di

import com.maxlabs.asdemo.data.Repository
import com.maxlabs.asdemo.data.api.InspectionApiService
import com.maxlabs.asdemo.data.model.RepositoryImpl
import com.maxlabs.asdemo.data.model.local.LocalDataSource
import com.maxlabs.asdemo.data.model.remote.RemoteDataSource
import com.maxlabs.asdemo.data.model.remote.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource): Repository {
        return RepositoryImpl(remoteDataSource, localDataSource)
    }
}