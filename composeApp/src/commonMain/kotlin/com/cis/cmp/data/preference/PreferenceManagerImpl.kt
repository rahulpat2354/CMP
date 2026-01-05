package com.cis.cmp.data.preference

import com.cis.cmp.data.models.LocalUserData
import com.russhwolf.settings.Settings
import kotlinx.serialization.json.Json

class PreferenceManagerImpl(private val settings: Settings) : PreferenceManager {

    companion object{
        private const val KEY_EMAIL = "user_email"
        private const val KEY_REMEMBER_ME = "remember_me"
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_USER_DATA = "user_data"
    }


    override fun saveEmail(email: String) {
        settings.putString(KEY_EMAIL, email)
    }

    override fun getEmail(): String? {
        return settings.getStringOrNull(KEY_EMAIL)
    }

    override fun clearEmail() {
        settings.remove(KEY_EMAIL)
    }

    override fun setRememberMe(remember: Boolean) {
        settings.putBoolean(KEY_EMAIL, remember)
    }

    override fun isRememberMeEnabled(): Boolean {
        return settings.getBoolean(KEY_REMEMBER_ME, false)
    }

    override fun saveAuthToken(token: String) {
        settings.putString(KEY_AUTH_TOKEN, token)
    }

    override fun getAuthToken(): String? {
        return settings.getStringOrNull(KEY_AUTH_TOKEN)
    }

    override fun clearAuthToken() {
        settings.remove(KEY_AUTH_TOKEN)
    }

    override fun isLoggedIn(): Boolean {
        return getAuthToken() != null
    }

    override fun clearAll() {
        settings.clear()
    }

    override fun saveUserData(user: LocalUserData) {
        settings.putString(KEY_USER_DATA, Json.encodeToString(user))
    }

    override fun getUserData(): LocalUserData? {
        val userDataJson = settings.getStringOrNull(KEY_USER_DATA)
        return userDataJson?.let { Json.decodeFromString(it) }
    }
}