package com.maxlabs.asdemo.presenter.viewModels

import android.util.Log
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

    val TAG = "InspectionViewModel"
    /**
     * Get inspections from the API
     */
    fun getInspectionFromAPI() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val result = inspectionUsecase.execute()
                _inspectionResult.postValue(result)
            }
        } catch (e: Exception) {
            Log.d(TAG, "getInspectionFromAPI: ${e.message}")
        }
    }

    /**
     * Submit inspections to the API
     */
    fun submitInspection(inspection: Inspection) {

        try {
            viewModelScope.launch(Dispatchers.IO) {
                val result = inspectionUsecase.submitInspection(inspection)
                _inspectionSubmitResult.postValue(result)
            }
        } catch (e: Exception) {
            Log.d(TAG, "submitInspection: ${e.message}")
        }
    }

    /**
     * Get inspections from the database
     */
    fun getInspectionFromDB() {

        try {
            viewModelScope.launch(Dispatchers.IO) {
                val result = inspectionUsecase.getInspectionFromDB()
                _inspectionResult.postValue(result)
            }
        } catch (e: Exception) {
            Log.d(TAG, "getInspectionFromDB: ${e.message}")
        }
    }

    /**
     * Update selected answer choice in the database
     */
    fun updateSelectedAnswer(questionId: Int, selectedAnswerChoiceId: Int) {
        try {
            viewModelScope.launch {
                inspectionUsecase.updateSelectedAnswerChoice(questionId, selectedAnswerChoiceId)
            }
        } catch (e: Exception) {
            Log.d(TAG, "updateSelectedAnswer: ${e.message}")
        }
    }

    /**
     * Clear all data from the database
     */
    fun clearAllData() {
        try {
            viewModelScope.launch {
                inspectionUsecase.clearAllData()
            }
        } catch (e: Exception) {
            Log.d(TAG, "clearAllData: ${e.message}")
        }
    }


}