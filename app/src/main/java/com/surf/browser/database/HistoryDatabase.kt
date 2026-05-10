package com.surf.browser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.surf.browser.database.entities.HistoryItem
import com.surf.browser.utils.DateConverter

@Database(entities = [HistoryItem::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class HistoryDatabase : RoomDatabase() {
    
    abstract fun historyDao(): HistoryDao
    
    companion object {
        @Volatile
        private var INSTANCE: HistoryDatabase? = null
        
        fun getDatabase(context: Context): HistoryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryDatabase::class.java,
                    "history_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}