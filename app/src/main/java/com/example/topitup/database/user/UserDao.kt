package com.example.topitup.database.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    //id,username,password are Not needed in this context
    //TODO Figure out later
    @Query("SELECT id, user_name, password, name, cards_scanned, points FROM user ORDER BY cards_scanned DESC")
    fun getAll(): Flow<List<User>>

    @Query("SELECT Count(id) as total_users, SUM(cards_scanned) as total_cards," +
            " SUM(points) as total_points, (SELECT name FROM User " +
            "ORDER BY points DESC LIMIT 1) as leader FROM USER")
    fun getTopUsers(): Flow<List<TopUser>>

    //id,username,password are Not needed in this context
    //TODO Figure out later
    @Query("SELECT id, user_name, password, name, cards_scanned, points FROM user WHERE name = :name")
    fun getUserDetail(name: String): Flow<List<User>>


}