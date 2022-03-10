package com.example.topitup.database.history

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT id, user_id, card_number, date, time FROM History ORDER BY date DESC")
    fun getAll(): Flow<List<History>>
}