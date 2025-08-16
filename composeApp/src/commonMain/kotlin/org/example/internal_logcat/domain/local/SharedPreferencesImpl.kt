package org.example.internal_logcat.domain.local

import com.russhwolf.settings.Settings

class SharedPreferencesImpl(private val settings: Settings) {

    private val loginKey = "isLogin"
    private val userIdKey = "userId"
    private val tokenKey = "token"
    private val userTypeKey = "user_type"
    private val passKey = "password"

    fun saveLoginState(isLogin: Boolean) {
        settings.putBoolean(loginKey, isLogin)
    }
    fun getLoginState(): Boolean {
        return settings.getBoolean(loginKey, false)
    }
    fun saveUserId(userId: String) {
        settings.putString(userIdKey, userId)
    }
    fun getUserId(): String {
        return settings.getString(userIdKey, "")
    }
    fun saveToken(token: String) {
        settings.putString(tokenKey, token)
    }
    fun getToken(): String {
        return settings.getString(tokenKey, "")
    }
    fun saveUserType(userType: String) {
        settings.putString(userTypeKey, userType)
    }
    fun getUserType(): String {
        return settings.getString(userTypeKey, "Production")
    }
    fun savePassword(password: String) {
        settings.putString(passKey, password)
    }
    fun getPassword(): String {
        return settings.getString(passKey, "")
    }
    fun clear() {
        settings.clear()
    }
    fun remove(key: String) {
        settings.remove(key)
    }
}