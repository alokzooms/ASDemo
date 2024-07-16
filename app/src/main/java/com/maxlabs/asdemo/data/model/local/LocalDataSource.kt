package com.maxlabs.asdemo.data.model.local

import com.maxlabs.asdemo.model.Inspection
import com.maxlabs.asdemo.model.InspectionModel

interface LocalDataSource {

    suspend fun getInspection(): Inspection
    suspend fun saveInspection(inspection: Inspection)
    suspend fun updateInspection(inspection: Inspection)
    suspend fun updateSelectedAnswerChoice(questionId: Int, selectedAnswerChoiceId: Int)
    suspend fun clearAllData(): Int

}