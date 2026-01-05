package com.cis.cmp.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cis.cmp.data.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class SplashUIState{
    object Loading: SplashUIState()
    object NavigateToHome: SplashUIState()
    object NavigateToLogin: SplashUIState()
}

class SplashViewModel(val authRepository: AuthRepository): ViewModel() {

    val splashState = MutableStateFlow<SplashUIState>(SplashUIState.Loading)

    init {
        checkUserLoginStatus()
    }

    fun checkUserLoginStatus(){
        viewModelScope.launch {
            val isAuthenticated = authRepository.isAuthenticated()
            delay(2000)
            splashState.value = if (isAuthenticated) SplashUIState.NavigateToHome else SplashUIState.NavigateToLogin
        }
    }
}