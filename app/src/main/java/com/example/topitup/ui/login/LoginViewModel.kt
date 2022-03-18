package com.example.topitup.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topitup.R
import com.example.topitup.data.LoginRepository
import com.example.topitup.data.Result
import kotlinx.coroutines.*

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        viewModelScope.launch {
            val result = loginRepository.login(username, password)

            if (result is Result.Success) {
                _loginResult.value =
                    LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
            } else {
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }
    }
    suspend fun register(username: String, password: String){
        viewModelScope.launch {
            val result = loginRepository.register(username, password)
            //TODO - Make relevant to Registration
            if (result is Result.Success) {
                _loginResult.value =
                    LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
            } else {
                _loginResult.value = LoginResult(error = R.string.login_failed)
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

    // username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.length > 1) {
            //Patterns.EMAIL_ADDRESS.matcher(username).matches()
            username.matches(Regex("^[A-Z]{3}[0-9]{3}"))
        } else {
            username.isNotBlank()
        }
    }

    // password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length == 6
    }
}