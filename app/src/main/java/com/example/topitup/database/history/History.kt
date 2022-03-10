package com.example.topitup.database.history

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History(
    @PrimaryKey val id: Int,
    @NonNull @ColumnInfo(name = "user_id") val usedId: Int,
    @NonNull @ColumnInfo(name = "card_number") val cardNumber: String,
    @NonNull @ColumnInfo(name = "date") val date: String,
    @NonNull @ColumnInfo(name = "time") val time: String,
)