package com.example.topitup.data

import android.util.Log
import com.example.topitup.data.model.LoggedInUser
import com.example.topitup.database.user.User
import com.example.topitup.database.user.UserDao
import java.io.IOException


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(private val userDao: UserDao) {


    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        Log.d("Login", "Login username: $username")
        Log.d("Login", "Login password: $password")
        suspend fun getLoginDetails(): User = userDao.getLogin(username, password)

        return try {
            // TODO: handle loggedInUser authentication
            Log.d("Login", "Database username: " + getLoginDetails().username)
            Log.d("Login", "Database password: " + getLoginDetails().password)
            Log.d("Login", "Database name: " + getLoginDetails().name)

            val user = LoggedInUser(
                getLoginDetails().username,
                getLoginDetails().name
            )
         Result.Success(user)

        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

     suspend fun register(user: User): Result<LoggedInUser>  {

         suspend fun addUser(user: User) = userDao.addUser(user)
          try {
              addUser(user)
          } catch (e: Throwable) {
              Log.d("Register", "ERROR", e)
          }

        return try {
             login(user.username, user.password)
        } catch (e: Throwable) {
            Result.Error(IOException("Error registering", e))
        }

    }
    fun logout() {
        // TODO: revoke authentication
    }
}