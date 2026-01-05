package com.cis.cmp.data.preference

import com.cis.cmp.data.models.LocalUserData

interface PreferenceManager {
    fun saveEmail(email: String)
    fun getEmail(): String?
    fun clearEmail()

    fun setRememberMe(remember: Boolean)
    fun isRememberMeEnabled(): Boolean

    fun saveAuthToken(token: String)
    fun getAuthToken(): String?
    fun clearAuthToken()

    fun isLoggedIn(): Boolean
    fun clearAll()

    fun saveUserData(user: LocalUserData)
    fun getUserData(): LocalUserData?
}