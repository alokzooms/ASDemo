package com.maxlabs.asdemo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.maxlabs.asdemo.data.db.converters.AnswerChoiceListConverter
import com.maxlabs.asdemo.data.db.converters.CategoryListConverter
import com.maxlabs.asdemo.data.db.converters.QuestionListConverter
import com.maxlabs.asdemo.data.db.dao.AnswerChoiceDao
import com.maxlabs.asdemo.data.db.dao.AreaDao
import com.maxlabs.asdemo.data.db.dao.CategoryDao
import com.maxlabs.asdemo.data.db.dao.InspectionDao
import com.maxlabs.asdemo.data.db.dao.InspectionTypeDao
import com.maxlabs.asdemo.data.db.dao.QuestionDao
import com.maxlabs.asdemo.data.db.dao.SurveyDao
import com.maxlabs.asdemo.model.AnswerChoice
import com.maxlabs.asdemo.model.Area
import com.maxlabs.asdemo.model.Category
import com.maxlabs.asdemo.model.Inspection
import com.maxlabs.asdemo.model.InspectionType
import com.maxlabs.asdemo.model.Question
import com.maxlabs.asdemo.model.Survey

@Database(
    entities = [AnswerChoice::class, Question::class, Category::class, Survey::class, InspectionType::class, Area::class, Inspection::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(AnswerChoiceListConverter::class, QuestionListConverter::class, CategoryListConverter::class)
abstract class InspectionDB : RoomDatabase() {
    abstract fun answerChoiceDao(): AnswerChoiceDao
    abstract fun questionDao(): QuestionDao
    abstract fun categoryDao(): CategoryDao
    abstract fun surveyDao(): SurveyDao
    abstract fun inspectionTypeDao(): InspectionTypeDao
    abstract fun areaDao(): AreaDao
    abstract fun inspectionDao(): InspectionDao
}
