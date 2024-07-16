package com.maxlabs.asdemo.data.model.local

import android.util.Log
import com.maxlabs.asdemo.data.db.dao.InspectionDao
import com.maxlabs.asdemo.data.db.dao.QuestionDao
import com.maxlabs.asdemo.model.Inspection
import com.maxlabs.asdemo.model.InspectionModel

class LocalDataSourceImpl(private val inspectionDAO: InspectionDao, private val questionDAO: QuestionDao): LocalDataSource {
    override suspend fun getInspection(): Inspection {
        return  inspectionDAO.getAllInspections()
    }
    /*override suspend fun getInspection(): InspectionModel {
        return inspectionDAO.
    }*/

    override suspend fun saveInspection(inspection: Inspection) {
        Log.d("LocalDataSourceImpl", "saveInspection: $inspection")
        inspectionDAO.insert(inspection)
        Log.d("LocalDataSourceImpl", "saveInspection saved: ")
    }

    override suspend fun updateInspection(inspection: Inspection) {
        Log.d("LocalDataSourceImpl", "updateInspection: $inspection")
        inspectionDAO.updateInspection(inspection)
        Log.d("LocalDataSourceImpl", "updateInspection saved: ")
    }

    override suspend fun updateSelectedAnswerChoice(questionId: Int, selectedAnswerChoiceId: Int) {
        var inspection = getInspection()
        inspection.survey.categories.forEach outerLoop@ { category ->
            category.questions.forEach { question ->
                if (question.id == questionId) {
                    question.selectedAnswerChoiceId = selectedAnswerChoiceId
                    Log.d("LocalDataSourceImpl", "condition satiesfied, breaking the loop :")
                    return@outerLoop
                }
            }
        }
        updateInspection(inspection)
    }

    override suspend fun clearAllData(): Int {
        return inspectionDAO.clearAllData()
    }


}