package com.example.eventfinder

import android.app.LauncherActivity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventfinder.Database.DatabaseAPP
import com.example.eventfinder.adapters.EventAdapter
import com.example.eventfinder.entities.Event
import com.example.eventfinder.helpers.MockDataLoader
import java.util.Locale

class ViewerActivity : AppCompatActivity() {

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "com.example.eventfinder"
    private val description = "Test notification"
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var searchList: ArrayList<Event>
    private lateinit var dataList: ArrayList<Event>
    private var userLocation: String = ""
    private var userCategory: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_combobox)
        val userId: Long = intent.getLongExtra("ARGUMENT_KEY", 0)
        DatabaseAPP.buildInstance(applicationContext)
        MockDataLoader.loadMockData()
        val currentUser = DatabaseAPP.getInstance().UsersDAO().getUserById(userId);
        if(currentUser != null){
            userLocation = currentUser.KeyInterestCity;
            userCategory = currentUser.KeyInterestCategory;
        }
        Log.d("ViewerActivity", "Jebeni korisnik: $currentUser ")


        val events = DatabaseAPP.getInstance().getEventsDao().getAllEvents()

        searchList = arrayListOf<Event>()
        dataList = ArrayList(events)
        searchList.addAll(dataList)


        searchView = findViewById(R.id.search)
        recyclerView = findViewById(R.id.events)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EventAdapter(searchList)

        searchView.clearFocus()
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                Log.d("ViewerActivity", "Search text: ${searchText}")
                if(searchText.isNotEmpty()){
                    dataList.forEach{
                        if(it.name.toLowerCase(Locale.getDefault()).contains(searchText)){
                            searchList.add(it)
                        }
                    }
                } else{
                    searchList.clear()
                    searchList.addAll(dataList)
                }
                recyclerView.adapter?.notifyDataSetChanged()
                return true
            }

        })

        val items = listOf("Svi","Preporučeno","Zabavni", "Edukativni", "Volonterski")
        val autoComplete: AutoCompleteTextView = findViewById(R.id.auto_complete)
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        autoComplete.setAdapter(adapter)

        val imageView: ImageView = findViewById(R.id.slika)
        imageView.setOnClickListener {
            val intent = Intent(this, MapsFragment::class.java)
            startActivity(intent)
        }

        autoComplete.setOnItemClickListener { _, _, position, _ ->
            val itemSelected = adapter.getItem(position).toString()
            filterEvents(itemSelected)
        }

        val btnClearFilters: Button = findViewById(R.id.btn_clear_filters)
        btnClearFilters.setOnClickListener {
            clearFilters()
        }

        val btnFavoriti: Button = findViewById(R.id.btn_favoriti)
        btnFavoriti.setOnClickListener {
            filterFavoriteEvents()
        }

        createNotification()
    }
    private fun getRandomEvent(): Event {
        val allEvents = DatabaseAPP.getInstance().getEventsDao().getAllEvents()
        val randomIndex = (0 until allEvents.size).random()
        return allEvents[randomIndex]
    }

    private fun createNotification(){
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val randomEvent = getRandomEvent()

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(randomEvent.urlOrganizer))
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            browserIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this,channelId)
                .setContentTitle("Preporučeno za vas")
                .setContentText("${randomEvent.name}")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_foreground))
                .setContentIntent(pendingIntent)
        }else{
            builder = Notification.Builder(this)
                .setContentTitle("Preporučeno za vas")
                .setContentText("${randomEvent.name}")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_foreground))
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234,builder.build())
    }

    private fun filterEvents(category: String) {
        // Retrieve saved city names
        val savedCityNames = getSavedCityNames()
        Log.d("ViewerActivity", "Saved City Names Status: ${savedCityNames.size}")
        Log.d("ViewerActivity", "Saved City Names: $savedCityNames")
        val filteredEvents = when (category) {
            "Svi" -> {
                if (savedCityNames.isEmpty()) {
                    // No city names are selected, filter only by category
                    DatabaseAPP.getInstance().getEventsDao().getAllEvents()
                } else {
                    // City names are selected, filter by both category and city names
                    val eventsDao = DatabaseAPP.getInstance().getEventsDao()
                    eventsDao.getEventsInCities(savedCityNames)
                }
            }
            "Preporučeno" -> {
                if (userLocation.isNotEmpty() && userCategory.isNotEmpty()) {
                    DatabaseAPP.getInstance().getEventsDao().getEventsByLocationAndCategory(userLocation, userCategory)
                } else {
                    // Fallback to all events if user location or category is not available
                    DatabaseAPP.getInstance().getEventsDao().getAllEvents()
                }
            }
            else -> {
                // Filter by category, and optionally by city names if they are selected
                val eventsDao = DatabaseAPP.getInstance().getEventsDao()
                val categoryFilteredEvents = eventsDao.getAllEvents()
                    .filter { event -> event.category.name == category }

                if (savedCityNames.isNotEmpty()) {
                    categoryFilteredEvents.filter { event -> savedCityNames.contains(event.location) }
                } else {
                    categoryFilteredEvents
                }
            }
        }

        recyclerView.adapter = EventAdapter(filteredEvents.toMutableList())
    }
    private fun filterFavoriteEvents() {
        val favoriteEvents = DatabaseAPP.getInstance().getEventsDao().getAllEvents()
            .filter { event -> event.isFavorite}
        recyclerView.adapter = EventAdapter(favoriteEvents.toMutableList())
    }


    private fun getSavedCityNames(): List<String> {
        val sharedPreferences =
            getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val cityNamesString = sharedPreferences.getString("cityNames", "") ?: ""
        val savedCityNames = if (cityNamesString.isNotEmpty()) {
            cityNamesString.split(",")
        } else {
            emptyList()
        }

        // Log the saved city names
        Log.d("ViewerActivity", "Saved City Names: $savedCityNames")

        return savedCityNames
    }

    private fun clearFilters() {
        // Clear saved city names
        clearSavedCityNames()

        // Set combo box to "Svi"
        val autoComplete: AutoCompleteTextView = findViewById(R.id.auto_complete)
        autoComplete.setText("Svi", false)

        // Retrieve all events (no filtering)
        val allEvents = DatabaseAPP.getInstance().getEventsDao().getAllEvents()

        // Update RecyclerView with all events
        recyclerView.adapter = EventAdapter(allEvents.toMutableList())
    }

    private fun clearSavedCityNames() {
        val sharedPreferences =
            getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Clear the saved city names
        editor.putString("cityNames", "")
        editor.apply()
    }
}
