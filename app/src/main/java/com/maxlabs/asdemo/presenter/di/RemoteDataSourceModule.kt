package com.maxlabs.asdemo.presenter.di

import com.maxlabs.asdemo.data.api.InspectionApiService
import com.maxlabs.asdemo.data.model.remote.RemoteDataSource
import com.maxlabs.asdemo.data.model.remote.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(inspectionApiService: InspectionApiService): RemoteDataSource {
        return RemoteDataSourceImpl(inspectionApiService)
    }
}