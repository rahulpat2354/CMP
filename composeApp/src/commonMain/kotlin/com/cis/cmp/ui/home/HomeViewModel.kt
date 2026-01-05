package com.cis.cmp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cis.cmp.data.models.LocalUserData
import com.cis.cmp.data.models.MainDashboardResp
import com.cis.cmp.data.network.ApiResult
import com.cis.cmp.data.repository.AuthRepository
import com.cis.cmp.data.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val isLoading: Boolean = false,
    val error: String ? = null,
    val userData: LocalUserData? = null,
    val dashboardOverviewData: MainDashboardResp.MainDashData? = null
)

class HomeViewModel(val authRepository: AuthRepository, val mainRepository: MainRepository) :
    ViewModel() {

    private val _userDataState = MutableStateFlow<LocalUserData?>(LocalUserData())
    var userDataState: StateFlow<LocalUserData?> = _userDataState.asStateFlow()

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState())
    var homeUiState: StateFlow<HomeUiState> =
        _homeUiState.asStateFlow()

    init {
        _userDataState.value = authRepository.getLocalUserData()
        getDashboardOverviewData()
    }

    fun getDashboardOverviewData() {
        viewModelScope.launch {
            when (val response = mainRepository.getMainDashboardData()) {
                is ApiResult.Error -> {
                    val error = response.exception
                    val displayMessage = error.detail ?: error.title
                    _homeUiState.value = _homeUiState.value.copy(isLoading = false, error = displayMessage)
                }
                ApiResult.Loading -> {
                    _homeUiState.value = _homeUiState.value.copy(isLoading = true)
                }
                is ApiResult.Success<MainDashboardResp> -> {
                    _homeUiState.value = _homeUiState.value.copy(isLoading = false, dashboardOverviewData = response.data.data)
                }
            }
        }
    }
}