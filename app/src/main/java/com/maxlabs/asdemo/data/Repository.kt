package com.maxlabs.asdemo.data

import com.maxlabs.asdemo.model.AuthenticationModel
import com.maxlabs.asdemo.model.Inspection
import com.maxlabs.asdemo.util.Resource

interface Repository {

    suspend fun login(authenticationModel: AuthenticationModel): Resource<String>
    suspend fun getInspectionFromAPI(): Resource<Inspection>
    suspend fun updateSelectedAnswerChoice(questionId: Int, selectedAnswerChoiceId: Int)
    suspend fun getInspectionFromDB(): Resource<Inspection>
    suspend fun submitInspection(inspection: Inspection): Resource<String>
    suspend fun clearAllData()


}