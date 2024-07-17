package com.maxlabs.asdemo.presenter.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maxlabs.asdemo.model.usecase.InspectionUsecase

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class InspectionViewModelFactory(private val inspectionUsecase: InspectionUsecase, private val application: Application,) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InspectionViewModel::class.java)) {
            return InspectionViewModel(inspectionUsecase, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}