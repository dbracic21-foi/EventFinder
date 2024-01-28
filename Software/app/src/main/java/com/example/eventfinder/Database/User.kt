package com.example.eventfinder.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val firstName : String,
    val lastName : String,
    val username : String,
    val pasword: String,
    val Email : String,
    var KeyInterestCategory: String,
    var KeyInterestCity: String,
)
