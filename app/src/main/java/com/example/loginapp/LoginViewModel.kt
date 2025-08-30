package com.example.loginapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// This ViewModel will handle the business logic and state for the login screen.
// It will hold the username, password, and the login result.
class LoginViewModel : ViewModel() {

    // Hardcoded credentials for demonstration purposes.
    private val VALID_USERNAME = "user"
    private val VALID_PASSWORD = "password123"

    // LiveData to hold the current state of the login result.
    // The `_` prefix is a common convention for mutable live data that is private.
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    // Enum to represent the different states of the login process.
    sealed class LoginResult {
        object Success : LoginResult()
        data class Error(val message: String) : LoginResult()
        object Idle : LoginResult()
    }

    // This function is called from the Activity to attempt a login.
    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _loginResult.value = LoginResult.Error("Please fill in both fields.")
        } else if (username == VALID_USERNAME && password == VALID_PASSWORD) {
            _loginResult.value = LoginResult.Success
        } else {
            _loginResult.value = LoginResult.Error("Invalid username or password.")
        }
    }

    // This function resets the state of the login process.
    fun resetLoginState() {
        _loginResult.value = LoginResult.Idle
    }
}
