package com.maxlabs.asdemo.presenter.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maxlabs.asdemo.model.usecase.InspectionUsecase

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class InspectionViewModelFactory(private val inspectionUsecase: InspectionUsecase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InspectionViewModel::class.java)) {
            return InspectionViewModel(inspectionUsecase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}