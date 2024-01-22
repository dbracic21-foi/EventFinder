package com.example.eventfinder.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.eventfinder.Database.DatabaseAPP
import com.example.eventfinder.converters.DateConverter
import java.util.Date


@Entity (
    tableName = "events",
    foreignKeys=[ForeignKey(
        entity = EventCategory:: class,
        parentColumns = ["id"],
        childColumns = ["category_id"],
        onDelete = ForeignKey.RESTRICT
    )]
)
@TypeConverters(DateConverter::class)
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    @ColumnInfo(name="date") val date: Date,
    @ColumnInfo(name="category_id", index = true) val categoryId: Int,
    val location: String,
    val description: String,
    val urlOrganizer: String,



){
    @delegate:Ignore
    val category: EventCategory by lazy {
        DatabaseAPP.getInstance().getEventCategoriesDao().getCategoryById(categoryId)
    }
}
