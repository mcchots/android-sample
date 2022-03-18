package com.example.topitup.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.topitup.data.LoginDataSource
import com.example.topitup.data.LoginRepository
import com.example.topitup.database.user.UserDao

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {

            return LoginViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource(userDao)
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}