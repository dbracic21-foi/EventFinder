package com.example.eventfinder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventfinder.Database.DatabaseAPP
import com.example.eventfinder.adapters.EventAdapter
import com.example.eventfinder.helpers.MockDataLoader

class ViewerActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_combobox)

        DatabaseAPP.buildInstance(applicationContext)
        MockDataLoader.loadMockData()

        recyclerView = findViewById(R.id.events)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val categories = DatabaseAPP.getInstance().getEventCategoriesDao().getAllCategories()

        val events = DatabaseAPP.getInstance().getEventsDao().getAllEvents()
        recyclerView.adapter = EventAdapter(events.toMutableList())



        val items = listOf("Svi", "Zabavni", "Edukativni", "Volonterski")
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
    }
    private fun filterEvents(category: String) {
        val filteredEvents = when (category) {
            "Svi" -> DatabaseAPP.getInstance().getEventsDao().getAllEvents()
            else -> {
                val eventsDao = DatabaseAPP.getInstance().getEventsDao()
                val mockEvents = eventsDao.getAllEvents()
                mockEvents.filter { event -> event.category.name == category }
            }
        }
        recyclerView.adapter = EventAdapter(filteredEvents.toMutableList())
    }
}