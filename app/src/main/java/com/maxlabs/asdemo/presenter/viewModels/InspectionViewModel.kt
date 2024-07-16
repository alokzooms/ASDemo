package com.maxlabs.asdemo.presenter.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxlabs.asdemo.model.Inspection
import com.maxlabs.asdemo.model.usecase.InspectionUsecase
import com.maxlabs.asdemo.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
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
        val coroutineExceptionHandler = CoroutineExceptionHandler() { _, throwable ->
            Log.e("Network Error", "Error connecting to server", throwable)
        }
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val result = inspectionUsecase.execute()
            _inspectionResult.postValue(result)
        }
    }

    /**
     * Submit inspections to the API
     */
    fun submitInspection(inspection: Inspection) {
        val coroutineExceptionHandler = CoroutineExceptionHandler() { _, throwable ->
            Log.e("Network Error", "Error connecting to server", throwable)
        }
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val result = inspectionUsecase.submitInspection(inspection)
            _inspectionSubmitResult.postValue(result)
        }
    }

    /**
     * Get inspections from the database
     */
    fun getInspectionFromDB() {
        val coroutineExceptionHandler = CoroutineExceptionHandler() { _, throwable ->
            Log.e("DataBase Error", "Error while geiing data from DB", throwable)
        }
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val result = inspectionUsecase.getInspectionFromDB()
            _inspectionResult.postValue(result)
        }
    }

    /**
     * Update selected answer choice in the database
     */
    fun updateSelectedAnswer(questionId: Int, selectedAnswerChoiceId: Int) {
        val coroutineExceptionHandler = CoroutineExceptionHandler() { _, throwable ->
            Log.e("DataBase Error", "Error while geiing data from DB", throwable)
        }
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            inspectionUsecase.updateSelectedAnswerChoice(questionId, selectedAnswerChoiceId)
        }
    }

    /**
     * Clear all data from the database
     */
    fun clearAllData() {
        val coroutineExceptionHandler = CoroutineExceptionHandler() { _, throwable ->
            Log.e("DataBase Error", "Error while geiing data from DB", throwable)
        }
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            inspectionUsecase.clearAllData()
        }
    }
}
