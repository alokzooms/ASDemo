package com.maxlabs.asdemo.presenter

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope

import com.maxlabs.asdemo.R
import com.maxlabs.asdemo.model.AuthenticationModel
import com.maxlabs.asdemo.model.usecase.LoginUsecase
import com.maxlabs.asdemo.util.NetworkUtil
import com.maxlabs.asdemo.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val application: Application, private val loginUsecase: LoginUsecase) : AndroidViewModel(application) {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<Resource<String>>()
    val loginResult: LiveData<Resource<String>> = _loginResult

    fun login(username: String, password: String) {
        if (NetworkUtil.isInternetAvailable(application)) {
            Toast.makeText(application, "Internet Available", Toast.LENGTH_SHORT).show()

            viewModelScope.launch(Dispatchers.IO) {
                val result = loginUsecase.execute(AuthenticationModel(username, password))
                _loginResult.postValue(result)
            }
        }
    }

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