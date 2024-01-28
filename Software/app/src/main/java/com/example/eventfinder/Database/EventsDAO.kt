package com.example.eventfinder.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

import androidx.room.Update
import com.example.eventfinder.entities.Event

@Dao
interface EventsDAO {
    @Query("SELECT * FROM events WHERE id = :id")
    fun getEvent(id: Int): Event

    @Query("SELECT * FROM events")
    fun getAllEvents(): List<Event>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvent(vararg event: Event): List<Long>

    @Query("SELECT * FROM events WHERE location IN (:cityNames)")
    fun getEventsInCities(cityNames: List<String>): List<Event>

    @Query("SELECT * FROM events WHERE location = :location AND category_id = (SELECT id FROM event_categories WHERE name = :category)")
    fun getEventsByLocationAndCategory(location: String, category: String): List<Event>
  
    @Update
    fun updateEvent(event: Event);
}