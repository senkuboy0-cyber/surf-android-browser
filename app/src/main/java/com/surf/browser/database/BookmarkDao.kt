package com.surf.browser.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.surf.browser.database.entities.Bookmark
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: Bookmark)
    
    @Update
    suspend fun updateBookmark(bookmark: Bookmark)
    
    @Query("DELETE FROM bookmarks WHERE id = :bookmarkId")
    suspend fun deleteBookmark(bookmarkId: Long)
    
    @Query("DELETE FROM bookmarks")
    suspend fun deleteAllBookmarks()
    
    @Query("SELECT * FROM bookmarks ORDER BY updatedAt DESC")
    fun getAllBookmarks(): Flow<List<Bookmark>>
    
    @Query("SELECT * FROM bookmarks WHERE title LIKE :query OR url LIKE :query ORDER BY updatedAt DESC")
    fun searchBookmarks(query: String): Flow<List<Bookmark>>
    
    @Query("SELECT * FROM bookmarks WHERE folder = :folder ORDER BY updatedAt DESC")
    fun getBookmarksByFolder(folder: String): Flow<List<Bookmark>>
    
    @Query("SELECT DISTINCT folder FROM bookmarks")
    fun getAllFolders(): Flow<List<String>>
    
    @Query("UPDATE bookmarks SET title = :title, url = :url, updatedAt = :updatedAt WHERE id = :bookmarkId")
    suspend fun updateBookmarkById(bookmarkId: Long, title: String, url: String, updatedAt: Long)
    
    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE url = :url)")
    suspend fun bookmarkExists(url: String): Boolean
    
    @Query("SELECT * FROM bookmarks WHERE url = :url LIMIT 1")
    suspend fun getBookmarkByUrl(url: String): Bookmark?
    
    @Query("UPDATE bookmarks SET folder = :newFolder WHERE id = :bookmarkId")
    suspend fun moveBookmarkToFolder(bookmarkId: Long, newFolder: String)
    
    @Query("DELETE FROM bookmarks WHERE folder = :folder")
    suspend fun deleteBookmarksByFolder(folder: String)
}