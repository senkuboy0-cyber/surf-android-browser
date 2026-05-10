package com.surf.browser.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.surf.browser.database.entities.HistoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(historyItem: HistoryItem)
    
    @Update
    suspend fun updateHistory(historyItem: HistoryItem)
    
    @Query("DELETE FROM history WHERE id = :historyId")
    suspend fun deleteHistory(historyId: Long)
    
    @Query("DELETE FROM history")
    suspend fun clearHistory()
    
    @Query("SELECT * FROM history ORDER BY visitTime DESC")
    fun getAllHistory(): Flow<List<HistoryItem>>
    
    @Query("SELECT * FROM history WHERE title LIKE :query OR url LIKE :query ORDER BY visitTime DESC")
    fun searchHistory(query: String): Flow<List<HistoryItem>>
    
    @Query("SELECT * FROM history ORDER BY visitTime DESC LIMIT :limit")
    fun getRecentHistory(limit: Int): Flow<List<HistoryItem>>
    
    @Query("SELECT * FROM history ORDER BY visitCount DESC LIMIT :limit")
    fun getMostVisitedHistory(limit: Int): Flow<List<HistoryItem>>
    
    @Query("UPDATE history SET visitCount = visitCount + 1, visitTime = :visitTime WHERE url = :url")
    suspend fun incrementVisitCount(url: String, visitTime: Long)
    
    @Query("SELECT * FROM history WHERE url = :url LIMIT 1")
    suspend fun getHistoryByUrl(url: String): HistoryItem?
    
    @Query("SELECT EXISTS(SELECT 1 FROM history WHERE url = :url)")
    suspend fun historyExists(url: String): Boolean
    
    @Query("DELETE FROM history WHERE visitTime < :olderThanTimestamp")
    suspend fun deleteOldHistory(olderThanTimestamp: Long)
    
    @Query("SELECT COUNT(*) FROM history")
    suspend fun getHistoryCount(): Int
    
    @Query("SELECT * FROM history WHERE visitTime >= :sinceTimestamp ORDER BY visitTime DESC")
    fun getHistorySince(sinceTimestamp: Long): Flow<List<HistoryItem>>
    
    @Query("SELECT DISTINCT SUBSTR(url, 1, INSTR(url, '/') - 1) FROM history WHERE INSTR(url, '/') > 0")
    fun getDomainVisits(): Flow<List<String>>
}