package com.cis.cmp.ui.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cis.cmp.data.models.LoginResponse
import com.cis.cmp.data.models.toLocalUserData
import com.cis.cmp.data.network.ApiResult
import com.cis.cmp.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val isLoading: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val generalError: String? = null
)

sealed class LoginEvent {
    object LoginSuccess : LoginEvent()
    data class ShowError(val message: String) : LoginEvent()
}

class LoginViewModel(
    val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _events = MutableStateFlow<LoginEvent?>(null)
    val events: StateFlow<LoginEvent?> = _events.asStateFlow()

    init {
        loadSavedCredentials()
    }

    private fun loadSavedCredentials() {
        val savedEmail = ""
        val rememberMe = false

        _uiState.value = _uiState.value.copy(
            email = savedEmail ?: "",
            rememberMe = rememberMe
        )
    }

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            emailError = null,
            generalError = null
        )
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = null,
            generalError = null
        )
    }

    fun updateIsRemember(remember: Boolean) {
        _uiState.value = _uiState.value.copy(rememberMe = remember)
    }

    fun login() {
        val currentState = _uiState.value

        // Clear previous errors
        _uiState.value = currentState.copy(
            emailError = null,
            passwordError = null,
            generalError = null
        )

        // Validate email
        if (currentState.email.isBlank()) {
            _uiState.value = _uiState.value.copy(emailError = "Email is required")
            return
        }

        if (!isValidEmail(currentState.email)) {
            _uiState.value = _uiState.value.copy(emailError = "Please enter a valid email")
            return
        }

        // Validate password
        if (currentState.password.isBlank()) {
            _uiState.value = _uiState.value.copy(passwordError = "Password is required")
            return
        }

        if (currentState.password.length < 6) {
            _uiState.value = _uiState.value.copy(
                passwordError = "Password must be at least 6 characters"
            )
            return
        }

        // Start login
        viewModelScope.launch {

            when (val result = authRepository.login(
                email = currentState.email,
                password = currentState.password
            )) {
                is ApiResult.Success<LoginResponse> -> {
                    handleLoginSuccess(result.data, currentState.rememberMe, currentState.email)
                }

                is ApiResult.Error -> {
                    val error = result.exception
                    val displayMessage = error.detail ?: error.title

                    _uiState.value = _uiState.value.copy(isLoading = false, generalError = displayMessage)
                    _events.value = LoginEvent.ShowError(displayMessage)
                }
                is ApiResult.Loading -> {

                }
            }
        }
    }

    private fun handleLoginSuccess(
        loginResponse: LoginResponse, // Replace with your actual response type
        rememberMe: Boolean,
        email: String
    ) {
        if (rememberMe){
            authRepository.saveEmail(email)
        }
        else {
            authRepository.clearEmail()
        }

        authRepository.saveAuthToken(loginResponse.data.authToken)
        authRepository.saveLocalUserData(loginResponse.data.toLocalUserData())

        // Update UI state
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            emailError = null,
            passwordError = null,
            generalError = null
        )

        // Emit success event
        _events.value = LoginEvent.LoginSuccess
    }

    fun clearEvent() {
        _events.value = null
    }

    fun clearErrors() {
        _uiState.value = _uiState.value.copy(
            emailError = null,
            passwordError = null,
            generalError = null
        )
    }

    private fun isValidEmail(email: String): Boolean {
        // KMP-compatible email validation
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()
        return emailRegex.matches(email)
    }
}