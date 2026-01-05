package com.cis.cmp.data.repository

import com.cis.cmp.data.models.LocalUserData
import com.cis.cmp.data.models.LoginResponse
import com.cis.cmp.data.network.ApiResult

interface AuthRepository {
    suspend fun login(email: String, password: String): ApiResult<LoginResponse>
    fun isAuthenticated(): Boolean
    fun logout()
    fun getSavedEmail(): String?
    fun saveEmail(email: String)
    fun clearEmail()
    fun isRememberMeEnabled(): Boolean
    fun setRememberMe(enabled: Boolean)
    fun saveAuthToken(token: String)
    fun clearAuthToken(token: String)
    fun saveLocalUserData(user: LocalUserData)
    fun getLocalUserData(): LocalUserData?
}