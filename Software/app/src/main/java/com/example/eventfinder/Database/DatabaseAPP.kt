package com.example.eventfinder.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.eventfinder.entities.Event
import com.example.eventfinder.entities.EventCategory
import com.example.eventfinder.entities.Review

@Database(entities = [User::class, Event::class, EventCategory::class,Review::class], version = 17)
abstract class DatabaseAPP : RoomDatabase() {
    abstract  fun UsersDAO() : UsersDAO

    abstract fun getEventsDao(): EventsDAO
    abstract fun getEventCategoriesDao(): EventCategoriesDAO

    companion object{
        @Volatile
        private var implmentedInstance: DatabaseAPP? = null

        fun getInstance(): DatabaseAPP{
            if (implmentedInstance == null){
                throw NullPointerException("Database instance has not yet been created")
            }
            return implmentedInstance!!
        }

        fun buildInstance(context: Context){
            if(implmentedInstance == null){
                val instanceBuilder = Room.databaseBuilder(
                    context,
                    DatabaseAPP::class.java,
                    "events.db"
                )
                instanceBuilder.fallbackToDestructiveMigration()
                instanceBuilder.allowMainThreadQueries()
                instanceBuilder.build()

                implmentedInstance = instanceBuilder.build()
            }
        }
    }
}