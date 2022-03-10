package com.example.topitup.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.topitup.database.history.History
import com.example.topitup.database.history.HistoryDao
import com.example.topitup.database.user.User
import com.example.topitup.database.user.UserDao

@Database(entities = [User::class, History::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "user_database"
                )
                    .createFromAsset("database/topitup.db")
                    .fallbackToDestructiveMigration() //TODO: REMOVE THIS!!!
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }


}
