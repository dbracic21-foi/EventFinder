package com.example.eventfinder.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "reviews",
    foreignKeys = [ForeignKey(
        entity = Event::class,
        parentColumns = ["id"],
        childColumns = ["eventID"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Review(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class Review(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val eventID: Int,
    val rating: Float,
    val comment: String
)
