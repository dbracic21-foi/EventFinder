package com.example.eventfinder.entities

import java.util.Date

data class Event(
    val name: String,
    val date: Date,
    val category: EventCategory,
    val location: String,
    val description: String
)
