package com.example.topitup.database.user

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    //TODO Figure out later - id,username,password are Not needed in this context
    @Query("SELECT id, user_name, password, name, cards_scanned, points FROM user ORDER BY cards_scanned DESC")
    fun getAll(): Flow<List<User>>

    @Query("SELECT Count(id) as total_users, SUM(cards_scanned) as total_cards," +
            " SUM(points) as total_points, (SELECT name FROM User " +
            "ORDER BY points DESC LIMIT 1) as leader FROM USER")
    fun getTopUsers(): Flow<List<TopUser>>

    //TODO Figure out later - id,username,password are Not needed in this context
    @Query("SELECT id, user_name, password, name, cards_scanned, points FROM user WHERE name = :name")
    fun getUserDetail(name: String): Flow<List<User>>

    @Query("SELECT * FROM User WHERE user_name = :username AND password = :password")
    suspend fun getLogin(username: String, password: String): User

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)
}