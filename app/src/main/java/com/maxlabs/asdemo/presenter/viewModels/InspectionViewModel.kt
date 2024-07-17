package com.maxlabs.asdemo.presenter.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.maxlabs.asdemo.model.Inspection
import com.maxlabs.asdemo.model.usecase.InspectionUsecase
import com.maxlabs.asdemo.util.NetworkUtil
import com.maxlabs.asdemo.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InspectionViewModel(private val inspectionUsecase: InspectionUsecase,
                          private val application: Application
) : AndroidViewModel(application) {

    private val _inspectionResult = MutableLiveData<Resource<Inspection>>()
    val inspectionResult: LiveData<Resource<Inspection>> = _inspectionResult

    private val _inspectionSubmitResult = MutableLiveData<Resource<String>>()
    val inspectionSubmitResult: LiveData<Resource<String>> = _inspectionSubmitResult

    val TAG = "InspectionViewModel"

    /**
     * Get inspections from the API
     */
    fun getInspectionFromAPI() {
        if (NetworkUtil.isInternetAvailable(application)) {
            val coroutineExceptionHandler = CoroutineExceptionHandler() { _, throwable ->
                Log.e(TAG, "Error connecting to server", throwable)
            }
            viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
                val result = inspectionUsecase.execute()
                _inspectionResult.postValue(result)
            }
        } else {
            Log.e(TAG, "No internet connection")
            _inspectionResult.postValue(Resource.Error(400, "No internet connection"))
        }
    }

    /**
     * Submit inspections to the API
     */
    fun submitInspection(inspection: Inspection) {
        if (NetworkUtil.isInternetAvailable(application)) {
            val coroutineExceptionHandler = CoroutineExceptionHandler() { _, throwable ->
                Log.e(TAG, "Error connecting to server", throwable)
            }
            viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
                val result = inspectionUsecase.submitInspection(inspection)
                _inspectionSubmitResult.postValue(result)
            }
        } else {
            Log.e(TAG, "No internet connection")
            _inspectionResult.postValue(Resource.Error(400, "No internet connection"))
        }
    }

    /**
     * Get inspections from the database
     */
    fun getInspectionFromDB() {

        val coroutineExceptionHandler = CoroutineExceptionHandler() { _, throwable ->
            Log.e(TAG, "Error while geiing data from DB", throwable)
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
            Log.e(TAG, "Error while geiing data from DB", throwable)
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
            Log.e(TAG, "Error while geiing data from DB", throwable)
        }
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            inspectionUsecase.clearAllData()
        }
    }
}
