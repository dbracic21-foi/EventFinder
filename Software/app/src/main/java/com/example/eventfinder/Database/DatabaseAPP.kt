package com.example.eventfinder.Database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class DatabaseAPP : RoomDatabase() {
    abstract  fun UsersDAO() : UsersDAO
}