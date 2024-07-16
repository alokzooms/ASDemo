package com.maxlabs.asdemo.model.usecase

import com.maxlabs.asdemo.data.Repository
import com.maxlabs.asdemo.model.Inspection
import com.maxlabs.asdemo.model.InspectionModel
import com.maxlabs.asdemo.util.Resource

class InspectionUsecase(private val repository: Repository) {

    suspend fun execute(): Resource<Inspection> {
        return repository.getInspectionFromAPI()
    }

    suspend fun getInspectionFromDB(): Resource<Inspection> {
        return repository.getInspectionFromDB()
    }

    suspend fun updateSelectedAnswerChoice(questionId: Int, selectedAnswerChoiceId: Int) {
        repository.updateSelectedAnswerChoice(questionId, selectedAnswerChoiceId)
    }

    suspend fun submitInspection(inspection: Inspection):  Resource<String> {
        return repository.submitInspection(inspection)
    }

    suspend fun clearAllData() {
        return repository.clearAllData()
    }


}