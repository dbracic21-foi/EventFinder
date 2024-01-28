package com.example.eventfinder.helpers

import com.example.eventfinder.Database.DatabaseAPP
import com.example.eventfinder.entities.Event
import com.example.eventfinder.entities.EventCategory
import com.example.eventfinder.entities.Review
import java.util.Date

object MockDataLoader {
    fun loadMockData(){
        val eventsDao = DatabaseAPP.getInstance().getEventsDao()
        val eventCategoriesDao = DatabaseAPP.getInstance().getEventCategoriesDao()
        val reviewDao = DatabaseAPP.getInstance().getReviewsDao()

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
                Event(1,"Vojko V u tvornici kulture", Date(), dbCategories[0].id, "Zagreb", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.","https://www.eventim.hr/hr/ulaznice/vojko-v-zagreb-dom-sportova-494656/event.html", false),
                Event(2,"Radionica izrade web stranica", Date(),dbCategories[1].id, "Šibenik", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.","https://www.hck.hr/kako-pomoci/darujte-krv/dobrovoljno-davanje-krvi-165/165", false),
                Event(3,"Doniranje krvi", Date(), dbCategories[2].id, "Varaždin", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.","https://www.hck.hr/kako-pomoci/darujte-krv/dobrovoljno-davanje-krvi-165/165", false),
                Event(4,"Koncert Central Cee-a", Date(), dbCategories[0].id, "Ljubljana", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.","https://www.eventim.si/si/vstopnice/offline-x-central-cee-ljubljana-hala-l56-industrijska-cona-litostroj-603692/event.html", false),
                Event(5, "Stand-up show: Smijeh do suza", Date(), dbCategories[0].id, "Split", "Nasmijte se uz najbolje stand-up komičare u regiji.","https://www.ulaznice.hr/web/event/38/11588", false),
                Event(6, "Predavanje o umjetnoj inteligenciji", Date(), dbCategories[1].id, "Zagreb", "Saznajte najnovije informacije o razvoju umjetne inteligencije.","https://ticm.hr/javno-predavanje-i-panel-rasprava-o-umjetnoj-inteligenciji-umjetna-inteligencija-i-sto-nam-donosi-buducnost/", false),
                Event(7, "Volonterska akcija čišćenja parka", Date(), dbCategories[2].id, "Osijek", "Pridružite se volonterskoj akciji čišćenja okoliša i doprinesite zajednici.","https://licegrada.hr/akcija-ciscenja-parka-na-srednjacima-uz-hurr-ribafisha-i-cistece-medvjedice/", false),
                Event(8, "Koncert lokalnog benda", Date(), dbCategories[0].id, "Rijeka", "Uživajte u glazbi lokalnih izvođača na nezaboravnom koncertu.","https://www.eventim.hr/hr/ulaznice/tragom-olivera-osijek-dvorana-gradski-vrt-625633/event.html", false),
                Event(9, "Radionica digitalne fotografije", Date(), dbCategories[1].id, "Pula", "Naučite osnove digitalne fotografije i poboljšajte svoje vještine.","https://www.fotoklubzagreb.hr/tecajevi/", false),
                Event(10, "Donacija hrane u humanitarnoj udruzi", Date(), dbCategories[2].id, "Split", "Pomozite onima kojima je najpotrebnije donirajući hranu humanitarnoj udruzi.","https://www.zakon.hr/z/418/Zakon-o-humanitarnoj-pomo%C4%87i", false),
                Event(11, "Stand-up show: Smijeh bez granica", Date(), dbCategories[0].id, "Zadar", "Svjetski poznati komičari garantiraju večer smijeha.","https://www.estudent.hr/novosti/smijeh-bez-granica-na-prvoj-radionici-projekta-mozak-voli-zdravo", false),
                Event(12, "Predavanje o očuvanju okoliša", Date(), dbCategories[1].id, "Varaždin", "Saznajte kako možete doprinijeti očuvanju okoliša i smanjenju ekološkog utjecaja.","https://eko.zagreb.hr/zastita-okolisa/10", false),
                Event(13, "Volonterska akcija uređenja dječjeg vrtića", Date(), dbCategories[2].id, "Zagreb", "Pomozite uređenju prostora za najmlađe.","https://torpedo.media/u-oku-kamere-odrzana-eko-akcija-uredenja-okolisa-djecjeg-vrtica-vidrice-na-kozali-rijeka/", false),
                Event(14, "Koncert poznatog izvođača", Date(), dbCategories[0].id, "Split", "Uživajte u glazbenom spektaklu poznatog izvođača.","https://www.eventim.hr/hr/izvodjac/sting-195/profile.html", false),
                Event(15, "Radionica kreativnog pisanja", Date(), dbCategories[1].id, "Rijeka", "Razvijajte svoje vještine pisanja uz stručno vođenje.","https://www.kreativnopisanje.org/", false),
                Event(16, "Donacija odjeće i obuće izbjeglicama", Date(), dbCategories[2].id, "Osijek", "Pomozite izbjeglicama pružiti im osnovne potrepštine.","https://ckzg.hr/en/clothing-donation/", false),
                Event(17, "Koncert indie benda", Date(), dbCategories[0].id, "Pula", "Otkrijte nove glazbene talente na koncertu indie benda.","https://www.ticketmaster.pl/muzyka/alternative-indie-rock/60/events", false),
                Event(18, "Predavanje o raznolikosti u društvu", Date(), dbCategories[1].id, "Zadar", "Razgovarajte o važnosti raznolikosti i inkluzivnosti.","https://bs.eferrit.com/podsticanje-kulturne-raznolikosti-u-vasoj-skoli/", false),
                Event(19, "Volonterska akcija sadnje drveća", Date(), dbCategories[2].id, "Varaždin", "Pomozite očuvati prirodu sudjelovanjem u akciji sadnje drveća.","https://srbi.hr/prva-volonterska-akcija-daljskog-dvorista/", false),
                Event(20, "Koncert jazz sastava", Date(), dbCategories[0].id, "Zagreb", "Uživajte u glazbi jazz sastava na nezaboravnom koncertu.","https://www.jazz.hr/", false),
            )

            eventsDao.insertEvent(*events)




        }
        if (reviewDao.getAllReviews().isEmpty()) {
            val reviews = arrayOf(
                Review(eventID = 1, rating = 4.5f, comment = "Odlican koncert"),
                Review(eventID = 1, rating = 2.5f, comment = "Loš koncert"),
                Review(
                    eventID = 5,
                    rating = 4.0f,
                    comment = "Sjajan stand-up show! Smijali smo se do suza."
                ),
                Review(
                    eventID = 6,
                    rating = 4.8f,
                    comment = "Vrlo poučno predavanje o umjetnoj inteligenciji."
                ),
                Review(
                    eventID = 7,
                    rating = 3.5f,
                    comment = "Volonterska akcija čišćenja parka je bila korisna."
                ),
                Review(
                    eventID = 8,
                    rating = 4.2f,
                    comment = "Koncert lokalnog benda je bio nezaboravan."
                ),
                Review(
                    eventID = 9,
                    rating = 4.5f,
                    comment = "Radionica digitalne fotografije je bila vrlo informativna."
                ),
                Review(
                    eventID = 10,
                    rating = 3.7f,
                    comment = "Donacija hrane u humanitarnoj udruzi je bila divna."
                ),
                Review(
                    eventID = 11,
                    rating = 4.5f,
                    comment = "Smijeh bez granica na stand-up showu."
                ),
                Review(
                    eventID = 12,
                    rating = 4.0f,
                    comment = "Predavanje o očuvanju okoliša je bilo edukativno."
                ),
                Review(
                    eventID = 13,
                    rating = 3.2f,
                    comment = "Volonterska akcija uređenja dječjeg vrtića je bila izazovna."
                ),
                Review(
                    eventID = 14,
                    rating = 4.8f,
                    comment = "Koncert poznatog izvođača je bio spektakularan."
                ),
                Review(
                    eventID = 15,
                    rating = 4.2f,
                    comment = "Radionica kreativnog pisanja je bila inspirativna."
                ),
                Review(
                    eventID = 16,
                    rating = 3.5f,
                    comment = "Donacija odjeće i obuće izbjeglicama je plemenit čin."
                ),
                Review(
                    eventID = 17,
                    rating = 4.6f,
                    comment = "Koncert indie benda je oduševio publiku."
                ),
                Review(
                    eventID = 18,
                    rating = 4.1f,
                    comment = "Predavanje o raznolikosti u društvu je potrebno slušati."
                ),
                Review(
                    eventID = 19,
                    rating = 3.9f,
                    comment = "Volonterska akcija sadnje drveća je očuvala prirodu."
                ),
                Review(
                    eventID = 20,
                    rating = 4.7f,
                    comment = "Koncert jazz sastava je bio ugodno glazbeno iskustvo."
                ),
                Review(eventID = 1, rating = 4.2f, comment = "Još jedan nezaboravan koncert!"),
                Review(
                    eventID = 2,
                    rating = 3.5f,
                    comment = "Zanimljiva radionica, ali malo dugotrajna."
                ),
                Review(
                    eventID = 3,
                    rating = 4.7f,
                    comment = "Doniranje krvi je uvijek plemenito djelo."
                ),
                Review(eventID = 4, rating = 4.0f, comment = "Central Cee je oduševio publiku."),
                Review(eventID = 5, rating = 4.8f, comment = "Smijeh do suza uz stand-up show."),
                Review(
                    eventID = 6,
                    rating = 4.3f,
                    comment = "Fascinantno predavanje o umjetnoj inteligenciji."
                ),
                Review(
                    eventID = 7,
                    rating = 3.9f,
                    comment = "Volonterska akcija čišćenja parka je bila uspješna."
                ),
                Review(
                    eventID = 8,
                    rating = 4.5f,
                    comment = "Lokalni bend je pružio nevjerojatan koncert."
                ),
                Review(
                    eventID = 9,
                    rating = 4.1f,
                    comment = "Radionica digitalne fotografije je inspirirajuća."
                ),
                Review(
                    eventID = 10,
                    rating = 3.7f,
                    comment = "Donacija hrane je važna za zajednicu."
                ),
                Review(
                    eventID = 11,
                    rating = 4.6f,
                    comment = "Smijeh bez granica na stand-up showu."
                ),
                Review(
                    eventID = 12,
                    rating = 3.8f,
                    comment = "Predavanje o očuvanju okoliša je educiralo prisutne."
                ),
                Review(
                    eventID = 13,
                    rating = 3.0f,
                    comment = "Volonterska akcija uređenja dječjeg vrtića."
                ),
                Review(
                    eventID = 14,
                    rating = 4.9f,
                    comment = "Koncert poznatog izvođača je bio vrhunski."
                ),
                Review(
                    eventID = 15,
                    rating = 4.2f,
                    comment = "Radionica kreativnog pisanja potaknula maštu."
                ),
                Review(
                    eventID = 16,
                    rating = 3.5f,
                    comment = "Donacija odjeće i obuće izbjeglicama je srdačna gesta."
                ),
                Review(
                    eventID = 17,
                    rating = 4.7f,
                    comment = "Indie bend je oduševio svojom glazbom."
                ),
                Review(
                    eventID = 18,
                    rating = 4.4f,
                    comment = "Raznolikost u društvu je važna tema."
                ),
                Review(
                    eventID = 19,
                    rating = 3.9f,
                    comment = "Volonterska akcija sadnje drveća zaštita prirode."
                ),
                Review(
                    eventID = 20,
                    rating = 4.5f,
                    comment = "Jazz sastav je pružio ugodnu glazbenu atmosferu."
                )


            )
            reviewDao.insertReview(*reviews)
        }
    }
}