package com.example.eventfinder.helpers

import com.example.eventfinder.entities.Event
import com.example.eventfinder.entities.EventCategory
import java.util.Date

object MockDataLoader {
    fun getDemoData(): List<Event> = listOf(
        Event("Vojko V u tvornici kulture", Date(), EventCategory("Zabavni", "#000080"), "Zagreb", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."),
        Event("Radionica izrade web stranica", Date(), EventCategory("Edukativni", "#CCCCCC"), "Šibenik", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."),
        Event("Doniranje krvi", Date(), EventCategory("Volonterski", "#FF0000"), "Varaždin", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."),
        Event("Koncert Central Cee-a", Date(), EventCategory("Zabavni", "#000080"), "Ljubljana", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
    )
}