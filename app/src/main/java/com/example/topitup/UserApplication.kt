package com.example.topitup

import android.app.Application
import com.example.topitup.database.AppDatabase

class UserApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}