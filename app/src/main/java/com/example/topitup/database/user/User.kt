package com.example.topitup.database.user

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id: Int,
    @NonNull @ColumnInfo(name = "user_name") val username: String,
    @NonNull @ColumnInfo(name = "password") val password: String,
    @NonNull @ColumnInfo(name = "name") val name: String,
    @NonNull @ColumnInfo(name = "cards_scanned") val cardsScanned: Int,
    @ColumnInfo(name = "points") val points: Int,

    )

data class TopUser(
    val total_users: Int,
    val total_cards: Int,
    val total_points: Int,
    val leader: String,
)