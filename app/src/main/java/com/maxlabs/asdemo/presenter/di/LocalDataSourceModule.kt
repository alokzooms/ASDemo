package com.maxlabs.asdemo.presenter.di

import com.maxlabs.asdemo.data.db.dao.InspectionDao
import com.maxlabs.asdemo.data.db.dao.QuestionDao
import com.maxlabs.asdemo.data.model.local.LocalDataSource
import com.maxlabs.asdemo.data.model.local.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

class LocalDataSourceModule {
    @Provides
    @Singleton
    fun provideLocalDataSource(inspectionDAO: InspectionDao, questionDAO: QuestionDao): LocalDataSource {
        return LocalDataSourceImpl(inspectionDAO, questionDAO)
    }
}