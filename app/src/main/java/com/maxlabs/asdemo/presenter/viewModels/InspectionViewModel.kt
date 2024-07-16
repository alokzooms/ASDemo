package com.maxlabs.asdemo.presenter.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxlabs.asdemo.model.Inspection
import com.maxlabs.asdemo.model.usecase.InspectionUsecase
import com.maxlabs.asdemo.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InspectionViewModel(private val inspectionUsecase: InspectionUsecase) : ViewModel() {

    private val _inspectionResult = MutableLiveData<Resource<Inspection>>()
    val inspectionResult: LiveData<Resource<Inspection>> = _inspectionResult

    private val _inspectionSubmitResult = MutableLiveData<Resource<String>>()
    val inspectionSubmitResult: LiveData<Resource<String>> = _inspectionSubmitResult

    /**
     * Get inspections from the API
     */
    fun getInspectionFromAPI() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = inspectionUsecase.execute()
            _inspectionResult.postValue(result)
        }
    }

    /**
     * Submit inspections to the API
     */
    fun submitInspection(inspection: Inspection) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = inspectionUsecase.submitInspection(inspection)
            _inspectionSubmitResult.postValue(result)
        }
    }

    /**
     * Get inspections from the database
     */
    fun getInspectionFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = inspectionUsecase.getInspectionFromDB()
            _inspectionResult.postValue(result)
        }
    }

    /**
     * Update selected answer choice in the database
     */
    fun updateSelectedAnswer(questionId: Int, selectedAnswerChoiceId: Int) {
        viewModelScope.launch {
            inspectionUsecase.updateSelectedAnswerChoice(questionId, selectedAnswerChoiceId)
        }
    }

    /**
     * Clear all data from the database
     */
    fun clearAllData() {
        viewModelScope.launch {
            inspectionUsecase.clearAllData()
        }
    }


}