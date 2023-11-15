package com.example.eventfinder.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsersDAO {
    @Insert
    fun insertUser(user: User)

    @Query("Select * FROM users WHERE username = :username AND pasword = :password")
    fun getUserByUsernameAndPassword(username : String, password: String) : User?
}