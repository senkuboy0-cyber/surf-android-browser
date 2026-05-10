package com.surf.browser.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "surf_preferences")

class PreferenceManager(private val context: Context) {
    
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("surf_prefs", Context.MODE_PRIVATE)
    
    private val dataStore = context.dataStore
    
    // Theme preferences
    suspend fun setThemeMode(mode: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("theme_mode")] = mode
        }
    }
    
    suspend fun getThemeMode(): String {
        return dataStore.data.map { preferences ->
            preferences[stringPreferencesKey("theme_mode")] ?: "system"
        }.first()
    }
    
    // Privacy preferences
    suspend fun setPrivateMode(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey("private_mode")] = enabled
        }
    }
    
    suspend fun getPrivateMode(): Boolean {
        return dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey("private_mode")] ?: false
        }.first()
    }
    
    // Ad blocking preferences
    suspend fun setAdBlocking(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey("ad_blocking")] = enabled
        }
    }
    
    suspend fun getAdBlocking(): Boolean {
        return dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey("ad_blocking")] ?: true
        }.first()
    }
    
    // Data saver preferences
    suspend fun setDataSaver(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey("data_saver")] = enabled
        }
    }
    
    suspend fun getDataSaver(): Boolean {
        return dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey("data_saver")] ?: false
        }.first()
    }
    
    // Home page preferences
    suspend fun setHomePage(url: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("home_page")] = url
        }
    }
    
    suspend fun getHomePage(): String {
        return dataStore.data.map { preferences ->
            preferences[stringPreferencesKey("home_page")] ?: "https://www.google.com"
        }.first()
    }
    
    // Search engine preferences
    suspend fun setSearchEngine(engine: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("search_engine")] = engine
        }
    }
    
    suspend fun getSearchEngine(): String {
        return dataStore.data.map { preferences ->
            preferences[stringPreferencesKey("search_engine")] ?: "google"
        }.first()
    }
    
    // User agent preferences
    suspend fun setUserAgent(userAgent: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("user_agent")] = userAgent
        }
    }
    
    suspend fun getUserAgent(): String {
        return dataStore.data.map { preferences ->
            preferences[stringPreferencesKey("user_agent")] ?: getDefaultUserAgent()
        }.first()
    }
    
    // Download preferences
    suspend fun setDownloadPath(path: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("download_path")] = path
        }
    }
    
    suspend fun getDownloadPath(): String {
        return dataStore.data.map { preferences ->
            preferences[stringPreferencesKey("download_path")] ?: "/Download"
        }.first()
    }
    
    // Biometric preferences
    suspend fun setBiometricLock(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey("biometric_lock")] = enabled
        }
    }
    
    suspend fun getBiometricLock(): Boolean {
        return dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey("biometric_lock")] ?: false
        }.first()
    }
    
    // Sync preferences
    suspend fun setSyncEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey("sync_enabled")] = enabled
        }
    }
    
    suspend fun getSyncEnabled(): Boolean {
        return dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey("sync_enabled")] ?: false
        }.first()
    }
    
    // Privacy settings
    suspend fun setLocationTracking(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey("location_tracking")] = enabled
        }
    }
    
    suspend fun getLocationTracking(): Boolean {
        return dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey("location_tracking")] ?: false
        }.first()
    }
    
    suspend fun setCookieTracking(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey("cookie_tracking")] = enabled
        }
    }
    
    suspend fun getCookieTracking(): Boolean {
        return dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey("cookie_tracking")] ?: true
        }.first()
    }
    
    // Clear all preferences
    suspend fun clearAllPreferences() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
        sharedPreferences.edit().clear().apply()
    }
    
    // Legacy SharedPreferences methods for migration
    fun setLegacyPreference(key: String, value: Any) {
        when (value) {
            is Boolean -> sharedPreferences.edit().putBoolean(key, value).apply()
            is String -> sharedPreferences.edit().putString(key, value).apply()
            is Int -> sharedPreferences.edit().putInt(key, value).apply()
            is Float -> sharedPreferences.edit().putFloat(key, value).apply()
            is Long -> sharedPreferences.edit().putLong(key, value).apply()
        }
    }
    
    fun getLegacyPreference(key: String, defaultValue: Any): Any {
        return when (defaultValue) {
            is Boolean -> sharedPreferences.getBoolean(key, defaultValue)
            is String -> sharedPreferences.getString(key, defaultValue) ?: defaultValue
            is Int -> sharedPreferences.getInt(key, defaultValue)
            is Float -> sharedPreferences.getFloat(key, defaultValue)
            is Long -> sharedPreferences.getLong(key, defaultValue)
            else -> defaultValue
        }
    }
    
    private fun getDefaultUserAgent(): String {
        return "Mozilla/5.0 (Linux; Android 12; SM-G991B) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.6099.230 Mobile Safari/537.36"
    }
}