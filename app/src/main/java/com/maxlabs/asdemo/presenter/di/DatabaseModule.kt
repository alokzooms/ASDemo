package com.maxlabs.asdemo.presenter.di

import android.content.Context
import androidx.room.Room
import com.maxlabs.asdemo.data.db.InspectionDB
import com.maxlabs.asdemo.data.db.dao.AnswerChoiceDao
import com.maxlabs.asdemo.data.db.dao.AreaDao
import com.maxlabs.asdemo.data.db.dao.CategoryDao
import com.maxlabs.asdemo.data.db.dao.InspectionDao
import com.maxlabs.asdemo.data.db.dao.InspectionTypeDao
import com.maxlabs.asdemo.data.db.dao.QuestionDao
import com.maxlabs.asdemo.data.db.dao.SurveyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): InspectionDB {
        return Room.databaseBuilder(
            appContext,
            InspectionDB::class.java,
            "inspection_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideAnswerChoiceDao(database: InspectionDB): AnswerChoiceDao {
        return database.answerChoiceDao()
    }

    @Provides
    fun provideQuestionDao(database: InspectionDB): QuestionDao {
        return database.questionDao()
    }

    @Provides
    fun provideCategoryDao(database: InspectionDB): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    fun provideSurveyDao(database: InspectionDB): SurveyDao {
        return database.surveyDao()
    }

    @Provides
    fun provideInspectionTypeDao(database: InspectionDB): InspectionTypeDao {
        return database.inspectionTypeDao()
    }

    @Provides
    fun provideAreaDao(database: InspectionDB): AreaDao {
        return database.areaDao()
    }

    @Provides
    fun provideInspectionDao(database: InspectionDB): InspectionDao {
        return database.inspectionDao()
    }
}



