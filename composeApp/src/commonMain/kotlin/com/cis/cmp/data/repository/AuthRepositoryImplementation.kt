package com.cis.cmp.data.repository

import com.cis.cmp.data.api.ApiService
import com.cis.cmp.data.models.LocalUserData
import com.cis.cmp.data.models.LoginResponse
import com.cis.cmp.data.network.ApiResult
import com.cis.cmp.data.preference.PreferenceManager

class AuthRepositoryImplementation(
    private val apiService: ApiService,
    private val preferenceManager: PreferenceManager
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): ApiResult<LoginResponse> {
        return apiService.login(email, password)
    }

    override fun isAuthenticated(): Boolean {
        return preferenceManager.isLoggedIn()
    }

    override fun logout() {
        preferenceManager.clearAuthToken()
        if (!preferenceManager.isRememberMeEnabled()) {
            preferenceManager.clearEmail()
        }
    }

    override fun getSavedEmail(): String? {
        return if (preferenceManager.isRememberMeEnabled()) {
            preferenceManager.getEmail()
        } else {
            null
        }
    }

    override fun saveEmail(email: String) {
        preferenceManager.saveEmail(email)
    }

    override fun clearEmail() {
        preferenceManager.clearEmail()
    }

    override fun isRememberMeEnabled(): Boolean {
        return preferenceManager.isRememberMeEnabled()
    }

    override fun setRememberMe(enabled: Boolean) {
        preferenceManager.setRememberMe(enabled)
        if (!enabled) {
            preferenceManager.clearEmail()
        }
    }

    override fun saveAuthToken(token: String) {
        preferenceManager.saveAuthToken(token)
    }

    override fun clearAuthToken(token: String) {
        preferenceManager.clearAuthToken()
    }

    override fun saveLocalUserData(user: LocalUserData) {
        preferenceManager.saveUserData(user)
    }

    override fun getLocalUserData(): LocalUserData? {
        return preferenceManager.getUserData()
    }

}