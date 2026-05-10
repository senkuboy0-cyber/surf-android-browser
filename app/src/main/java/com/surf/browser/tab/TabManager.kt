package com.surf.browser.tab

import android.content.Context
import android.util.Log
import com.surf.browser.utils.PreferenceManager
import org.mozilla.geckoview.GeckoSession
import java.util.Stack

class TabManager(private val context: Context) {
    
    private val preferenceManager = PreferenceManager(context)
    private val sessions = Stack<GeckoSession>()
    private var currentSession: GeckoSession? = null
    
    init {
        // Create initial session
        addTab()
    }
    
    fun addTab(url: String? = null): GeckoSession {
        val session = GeckoSession()
        
        // Configure session settings
        val settings = session.settings
        settings.setJavaScriptEnabled(true)
        settings.setDomStorageEnabled(true)
        settings.setAllowFileAccess(true)
        
        // Add to stack
        sessions.push(session)
        currentSession = session
        
        // Load URL if provided
        url?.let {
            session.loadUri(it)
        }
        
        Log.d("TabManager", "Added new tab. Total tabs: ${sessions.size}")
        return session
    }
    
    fun removeTab(session: GeckoSession) {
        if (sessions.contains(session)) {
            sessions.remove(session)
            session.close()
            
            if (sessions.isEmpty()) {
                // No tabs left, create a new one
                addTab()
            } else {
                // Set current session to the top of stack
                currentSession = sessions.peek()
            }
            
            Log.d("TabManager", "Removed tab. Total tabs: ${sessions.size}")
        }
    }
    
    fun getCurrentSession(): GeckoSession {
        return currentSession ?: sessions.peek()
    }
    
    fun getTabCount(): Int {
        return sessions.size
    }
    
    fun getAllSessions(): List<GeckoSession> {
        return sessions.toList()
    }
    
    fun switchToTab(index: Int): GeckoSession? {
        return if (index in 0 until sessions.size) {
            currentSession = sessions[index]
            currentSession
        } else {
            null
        }
    }
    
    fun closeAllTabsExceptCurrent() {
        val current = getCurrentSession()
        sessions.clear()
        sessions.push(current)
        currentSession = current
    }
    
    fun getSessionsStack(): Stack<GeckoSession> {
        return sessions
    }
    
    fun setCurrentSession(session: GeckoSession) {
        if (sessions.contains(session)) {
            currentSession = session
        }
    }
    
    fun hasPreviousTab(): Boolean {
        return sessions.size > 1
    }
    
    fun getNextTab(): GeckoSession? {
        return if (sessions.size > 1) {
            sessions.elementAtOrNull(1)
        } else {
            null
        }
    }
    
    fun getPreviousTab(): GeckoSession? {
        return if (sessions.size > 1) {
            sessions.elementAtOrNull(0)
        } else {
            null
        }
    }
    
    fun clearAllSessions() {
        sessions.forEach { it.close() }
        sessions.clear()
        addTab()
    }
    
    fun saveTabsState(): List<String> {
        return sessions.map { session ->
            // This would need to be implemented to save URL state
            ""
        }
    }
    
    fun restoreTabsState(savedUrls: List<String>) {
        clearAllSessions()
        savedUrls.forEach { url ->
            if (url.isNotEmpty()) {
                addTab(url)
            }
        }
    }
}