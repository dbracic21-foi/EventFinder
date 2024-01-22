package com.example.eventfinder.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class Review(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val eventID: Int,
    val rating: Float,
    val comment: String
)
