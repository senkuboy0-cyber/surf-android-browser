package com.surf.browser

import android.app.Application
import androidx.room.Room
import com.surf.browser.database.BookmarkDatabase
import com.surf.browser.database.HistoryDatabase
import com.surf.browser.utils.PreferenceManager

class SurfApplication : Application() {
    
    lateinit var bookmarkDatabase: BookmarkDatabase
    lateinit var historyDatabase: HistoryDatabase
    lateinit var preferenceManager: PreferenceManager
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize databases
        bookmarkDatabase = Room.databaseBuilder(
            applicationContext,
            BookmarkDatabase::class.java,
            "bookmark_database"
        ).build()
        
        historyDatabase = Room.databaseBuilder(
            applicationContext,
            HistoryDatabase::class.java,
            "history_database"
        ).build()
        
        // Initialize preference manager
        preferenceManager = PreferenceManager(applicationContext)
        
        // Initialize GeckoView runtime
        GeckoRuntime.create(this)
    }
}