package com.example.eventfinder.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.eventfinder.entities.Review
@Dao
interface ReviewDAO {
    @Query("SELECT * FROM reviews")
    fun getAllReviews(): List<Review>

    @Query("SELECT * FROM reviews WHERE eventID = :eventId")
    fun getReviewsForEvent(eventId: Int): List<Review>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReview(vararg review: Review): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReviews(reviews: List<Review>)

}