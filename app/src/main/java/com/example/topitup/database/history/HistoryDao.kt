package com.example.topitup.database.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Transaction
    @Query("SELECT  id, name, card_number, date, time FROM History ORDER BY date DESC")
    fun getAll(): Flow<List<History>>

    @Query("SELECT id, name, card_number, date, time FROM History WHERE date = :date")
    fun searchDate(date: String): Flow<List<History>>

    @Insert
    fun insertTransaction(history: History)
}