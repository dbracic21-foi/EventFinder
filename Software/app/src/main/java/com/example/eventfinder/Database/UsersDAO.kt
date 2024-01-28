package com.example.eventfinder.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UsersDAO {
    @Insert
    fun insertUser(user: User)

    @Query("Select * FROM users WHERE (username = :username OR Email = :username) AND pasword = :password")
    fun getUserByUsernameAndPassword(username : String, password: String) : User?

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: Long): User?

    @Query("SELECT * FROM users ORDER BY id DESC LIMIT 1")
    fun getLastInsertedUser(): User?
    @Update
    fun updateUser(user: User)
}