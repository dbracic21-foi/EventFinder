package com.example.eventfinder.converters

import androidx.room.TypeConverter
import java.util.Date
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class DateConverter {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
    /*@TypeConverter
    fun fromDate(date: Date) : Long{
        return date.time
    }
    @TypeConverter
    fun toDate(timestamp: Long): Date{
        return Date(timestamp)
    }*/




}