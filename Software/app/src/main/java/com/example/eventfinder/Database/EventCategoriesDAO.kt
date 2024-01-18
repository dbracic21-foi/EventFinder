package com.example.eventfinder.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eventfinder.entities.EventCategory

@Dao
interface EventCategoriesDAO {
    @Query("SELECT * FROM event_categories")
    fun getAllCategories(): List<EventCategory>

    @Query("SELECT * FROM event_categories WHERE id = :id")
    fun getCategoryById(id: Int): EventCategory

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(vararg category: EventCategory)
}