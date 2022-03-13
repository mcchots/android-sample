package com.example.topitup.database.history

import androidx.annotation.NonNull
import androidx.room.*
import com.example.topitup.database.user.User

@Entity
data class History(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "card_number") val cardNumber: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time") val time: String,
)
