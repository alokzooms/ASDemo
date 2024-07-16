package com.maxlabs.asdemo.presenter.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maxlabs.asdemo.model.usecase.LoginUsecase

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory(private val application: Application,private val loginUsecase: LoginUsecase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(application, loginUsecase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}