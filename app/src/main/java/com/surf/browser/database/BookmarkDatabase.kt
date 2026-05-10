package com.surf.browser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.surf.browser.database.entities.Bookmark
import com.surf.browser.utils.DateConverter

@Database(entities = [Bookmark::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class BookmarkDatabase : RoomDatabase() {
    
    abstract fun bookmarkDao(): BookmarkDao
    
    companion object {
        @Volatile
        private var INSTANCE: BookmarkDatabase? = null
        
        fun getDatabase(context: Context): BookmarkDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookmarkDatabase::class.java,
                    "bookmark_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}