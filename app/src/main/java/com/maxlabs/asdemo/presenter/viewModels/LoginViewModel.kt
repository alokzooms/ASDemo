package com.maxlabs.asdemo.presenter.viewModels

import android.app.Application
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.maxlabs.asdemo.R
import com.maxlabs.asdemo.model.AuthenticationModel
import com.maxlabs.asdemo.model.usecase.LoginUsecase
import com.maxlabs.asdemo.presenter.LoginFormState
import com.maxlabs.asdemo.util.NetworkUtil
import com.maxlabs.asdemo.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val application: Application, private val loginUsecase: LoginUsecase) :
    AndroidViewModel(application) {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<Resource<String>>()
    val loginResult: LiveData<Resource<String>> = _loginResult

    val TAG = "LoginViewModel"

    /**
     * Method to login
     * username: String:email or username
     * password: String: password
     */
    fun login(username: String, password: String) {
        if (NetworkUtil.isInternetAvailable(application)) {
            val coroutineExceptionHandler = CoroutineExceptionHandler() { _, throwable ->
                Log.e("Network Error", "Error connecting to server", throwable)
            }

            viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
                val result = loginUsecase.execute(AuthenticationModel(username, password))
                _loginResult.postValue(result)
            }
        }
    }

    /**
     * Verfiy credentials and update UI
     */
    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}