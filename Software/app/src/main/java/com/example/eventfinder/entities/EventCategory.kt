package com.example.eventfinder.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "event_categories")
data class EventCategory(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val color: String
)
