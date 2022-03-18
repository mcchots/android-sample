package com.example.topitup.database.user

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_name") var username: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "cards_scanned") val cardsScanned: Int = 0,
    @ColumnInfo(name = "points") val points: Int = 0,

    )

data class TopUser(
    val total_users: Int,
    val total_cards: Int,
    val total_points: Int,
    val leader: String,
)