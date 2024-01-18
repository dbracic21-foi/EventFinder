package com.example.eventfinder.helpers

import com.example.eventfinder.Database.DatabaseAPP
import com.example.eventfinder.entities.Event
import com.example.eventfinder.entities.EventCategory
import java.util.Date

object MockDataLoader {
    fun loadMockData(){
        val eventsDao = DatabaseAPP.getInstance().getEventsDao()
        val eventCategoriesDao = DatabaseAPP.getInstance().getEventCategoriesDao()

        if(eventsDao.getAllEvents().isEmpty() &&
            eventCategoriesDao.getAllCategories().isEmpty()){
            val categories = arrayOf(
                EventCategory(1, "Zabavni", "#000080"),
                EventCategory(2, "Edukativni", "#CCCCCC"),
                EventCategory(3, "Volonterski", "#FF0000"),
            )
            DatabaseAPP.getInstance().getEventCategoriesDao().insertCategory(*categories)

            val dbCategories = eventCategoriesDao.getAllCategories()

            val events = arrayOf(
                Event(1,"Vojko V u tvornici kulture", Date(), dbCategories[0].id, "Zagreb", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."),
                Event(2,"Radionica izrade web stranica", Date(),dbCategories[1].id, "Šibenik", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."),
                Event(3,"Doniranje krvi", Date(), dbCategories[2].id, "Varaždin", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."),
                Event(4,"Koncert Central Cee-a", Date(), dbCategories[0].id, "Ljubljana", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."),
                Event(5, "Stand-up show: Smijeh do suza", Date(), dbCategories[0].id, "Split", "Nasmijte se uz najbolje stand-up komičare u regiji."),
                Event(6, "Predavanje o umjetnoj inteligenciji", Date(), dbCategories[1].id, "Zagreb", "Saznajte najnovije informacije o razvoju umjetne inteligencije."),
                Event(7, "Volonterska akcija čišćenja parka", Date(), dbCategories[2].id, "Osijek", "Pridružite se volonterskoj akciji čišćenja okoliša i doprinesite zajednici."),
                Event(8, "Koncert lokalnog benda", Date(), dbCategories[0].id, "Rijeka", "Uživajte u glazbi lokalnih izvođača na nezaboravnom koncertu."),
                Event(9, "Radionica digitalne fotografije", Date(), dbCategories[1].id, "Pula", "Naučite osnove digitalne fotografije i poboljšajte svoje vještine."),
                Event(10, "Donacija hrane u humanitarnoj udruzi", Date(), dbCategories[2].id, "Split", "Pomozite onima kojima je najpotrebnije donirajući hranu humanitarnoj udruzi."),
                Event(11, "Stand-up show: Smijeh bez granica", Date(), dbCategories[0].id, "Zadar", "Svjetski poznati komičari garantiraju večer smijeha."),
                Event(12, "Predavanje o očuvanju okoliša", Date(), dbCategories[1].id, "Varaždin", "Saznajte kako možete doprinijeti očuvanju okoliša i smanjenju ekološkog utjecaja."),
                Event(13, "Volonterska akcija uređenja dječjeg vrtića", Date(), dbCategories[2].id, "Zagreb", "Pomozite uređenju prostora za najmlađe."),
                Event(14, "Koncert poznatog izvođača", Date(), dbCategories[0].id, "Split", "Uživajte u glazbenom spektaklu poznatog izvođača."),
                Event(15, "Radionica kreativnog pisanja", Date(), dbCategories[1].id, "Rijeka", "Razvijajte svoje vještine pisanja uz stručno vođenje."),
                Event(16, "Donacija odjeće i obuće izbjeglicama", Date(), dbCategories[2].id, "Osijek", "Pomozite izbjeglicama pružiti im osnovne potrepštine."),
                Event(17, "Koncert indie benda", Date(), dbCategories[0].id, "Pula", "Otkrijte nove glazbene talente na koncertu indie benda."),
                Event(18, "Predavanje o raznolikosti u društvu", Date(), dbCategories[1].id, "Zadar", "Razgovarajte o važnosti raznolikosti i inkluzivnosti."),
                Event(19, "Volonterska akcija sadnje drveća", Date(), dbCategories[2].id, "Varaždin", "Pomozite očuvati prirodu sudjelovanjem u akciji sadnje drveća."),
                Event(20, "Koncert jazz sastava", Date(), dbCategories[0].id, "Zagreb", "Uživajte u glazbi jazz sastava na nezaboravnom koncertu."),
            )
            eventsDao.insertEvent(*events)
        }
    }
}