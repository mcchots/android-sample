package com.example.topitup.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.topitup.database.user.TopUser
import com.example.topitup.database.user.User
import com.example.topitup.database.user.UserDao
import kotlinx.coroutines.flow.Flow

class HomeViewModel(private val userDao: UserDao) : ViewModel() {
    fun getAll(): Flow<List<User>> = userDao.getAll()
    fun getTopStats(): Flow<List<TopUser>> = userDao.getTopUsers()
    //fun getUserDetails(name: String): Flow<List<User>> = userDao.getUserDetail(name)
}

class HomeViewModelFactory(
    private val userDao: UserDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}