package com.maxlabs.asdemo.presenter.di

import com.maxlabs.asdemo.data.Repository
import com.maxlabs.asdemo.model.usecase.InspectionUsecase
import com.maxlabs.asdemo.model.usecase.LoginUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UsecaseModule {
    @Provides
    @Singleton
    fun provideLoginUsecase(repository: Repository): LoginUsecase{
        return LoginUsecase(repository)
    }

    @Provides
    @Singleton
    fun provideInspectionUsecase(repository: Repository): InspectionUsecase{
        return InspectionUsecase(repository)
    }
}