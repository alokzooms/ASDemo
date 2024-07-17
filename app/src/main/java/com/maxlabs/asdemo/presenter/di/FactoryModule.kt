package com.maxlabs.asdemo.presenter.di

import android.app.Application
import com.maxlabs.asdemo.model.usecase.InspectionUsecase
import com.maxlabs.asdemo.model.usecase.LoginUsecase
import com.maxlabs.asdemo.presenter.viewModels.InspectionViewModelFactory
import com.maxlabs.asdemo.presenter.viewModels.LoginViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {
    @Singleton
    @Provides
    fun provideLoginViewModelFactory(
        application: Application,
        loginUsecase: LoginUsecase
    ): LoginViewModelFactory {
        return LoginViewModelFactory(application, loginUsecase)
    }

    @Singleton
    @Provides
    fun provideInspectionViewModelFactory(application: Application,inspectionUsecase: InspectionUsecase): InspectionViewModelFactory {
        return InspectionViewModelFactory(inspectionUsecase, application)
    }
}