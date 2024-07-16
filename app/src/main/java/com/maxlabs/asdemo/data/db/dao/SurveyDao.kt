package com.maxlabs.asdemo.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.maxlabs.asdemo.model.Survey

@Dao
interface SurveyDao {
    @Insert
    suspend fun insert(survey: Survey)

    @Query("SELECT * FROM survey_table")
    suspend fun getAllSurveys(): List<Survey>
}
