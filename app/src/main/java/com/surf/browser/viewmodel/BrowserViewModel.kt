package com.surf.browser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.surf.browser.database.BookmarkDatabase
import com.surf.browser.database.HistoryDatabase
import com.surf.browser.database.entities.Bookmark
import com.surf.browser.database.entities.HistoryItem
import com.surf.browser.tab.TabManager
import kotlinx.coroutines.launch
import org.mozilla.geckoview.GeckoSession

class BrowserViewModel(application: Application) : AndroidViewModel(application) {
    
    private val bookmarkDao = BookmarkDatabase.getDatabase(application).bookmarkDao()
    private val historyDao = HistoryDatabase.getDatabase(application).historyDao()
    
    private val _currentUrl = MutableLiveData<String>()
    val currentUrl: LiveData<String> = _currentUrl
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _canGoBack = MutableLiveData<Boolean>()
    val canGoBack: LiveData<Boolean> = _canGoBack
    
    private val _canGoForward = MutableLiveData<Boolean>()
    val canGoForward: LiveData<Boolean> = _canGoForward
    
    private val _pageTitle = MutableLiveData<String>()
    val pageTitle: LiveData<String> = _pageTitle
    
    private val _pageProgress = MutableLiveData<Int>()
    val pageProgress: LiveData<Int> = _pageProgress
    
    private val _securityStatus = MutableLiveData<Boolean>()
    val securityStatus: LiveData<Boolean> = _securityStatus
    
    fun updateCurrentUrl(url: String) {
        _currentUrl.value = url
    }
    
    fun updateLoadingState(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
    
    fun updateNavigationState(canGoBack: Boolean, canGoForward: Boolean) {
        _canGoBack.value = canGoBack
        _canGoForward.value = canGoForward
    }
    
    fun updatePageTitle(title: String) {
        _pageTitle.value = title
    }
    
    fun updatePageProgress(progress: Int) {
        _pageProgress.value = progress
    }
    
    fun updateSecurityStatus(isSecure: Boolean) {
        _securityStatus.value = isSecure
    }
    
    // Bookmark operations
    fun addBookmark(bookmark: Bookmark) {
        viewModelScope.launch {
            bookmarkDao.insertBookmark(bookmark)
        }
    }
    
    fun removeBookmark(bookmarkId: Long) {
        viewModelScope.launch {
            bookmarkDao.deleteBookmark(bookmarkId)
        }
    }
    
    fun getAllBookmarks(): LiveData<List<Bookmark>> {
        return bookmarkDao.getAllBookmarks()
    }
    
    fun searchBookmarks(query: String): LiveData<List<Bookmark>> {
        return bookmarkDao.searchBookmarks("%$query%")
    }
    
    // History operations
    fun addToHistory(historyItem: HistoryItem) {
        viewModelScope.launch {
            historyDao.insertHistory(historyItem)
        }
    }
    
    fun clearHistory() {
        viewModelScope.launch {
            historyDao.clearHistory()
        }
    }
    
    fun getAllHistory(): LiveData<List<HistoryItem>> {
        return historyDao.getAllHistory()
    }
    
    fun searchHistory(query: String): LiveData<List<HistoryItem>> {
        return historyDao.searchHistory("%$query%")
    }
    
    fun getRecentHistory(limit: Int): LiveData<List<HistoryItem>> {
        return historyDao.getRecentHistory(limit)
    }
    
    // Navigation helpers
    fun canNavigateBack(canGoBack: Boolean) {
        _canGoBack.value = canGoBack
    }
    
    fun canNavigateForward(canGoForward: Boolean) {
        _canGoForward.value = canGoForward
    }
    
    // Page state helpers
    fun updatePageState(url: String, title: String, isLoading: Boolean, progress: Int) {
        _currentUrl.value = url
        _pageTitle.value = title
        _isLoading.value = isLoading
        _pageProgress.value = progress
    }
    
    fun resetPageState() {
        _currentUrl.value = ""
        _pageTitle.value = ""
        _isLoading.value = false
        _pageProgress.value = 0
        _canGoBack.value = false
        _canGoForward.value = false
    }
    
    // Security helpers
    fun updateSecurityState(url: String, isSecure: Boolean) {
        _currentUrl.value = url
        _securityStatus.value = isSecure
    }
    
    // Tab management helpers
    fun getTabCount(tabManager: TabManager): Int {
        return tabManager.getTabCount()
    }
    
    fun addNewTab(tabManager: TabManager, url: String? = null): GeckoSession {
        return tabManager.addTab(url)
    }
    
    fun closeTab(tabManager: TabManager, session: GeckoSession) {
        tabManager.removeTab(session)
    }
}