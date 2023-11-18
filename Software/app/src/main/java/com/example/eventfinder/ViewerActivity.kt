package com.example.eventfinder

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventfinder.adapters.EventAdapter
import com.example.eventfinder.helpers.MockDataLoader

class ViewerActivity: AppCompatActivity() {

    private val mockEvents = MockDataLoader.getDemoData()
    private lateinit var recyclerView: RecyclerView





    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_combobox)

        recyclerView = findViewById(R.id.events)
        recyclerView.adapter = EventAdapter(MockDataLoader.getDemoData())
        recyclerView.layoutManager = LinearLayoutManager(this)





         val items = listOf("Svi", "Zabavni", "Edukativni", "Volonterski" )

         val autoComplete : AutoCompleteTextView = findViewById(R.id.auto_complete)

         val adapter = ArrayAdapter(this, R.layout.list_item, items)

         autoComplete.setAdapter(adapter)



        autoComplete.onItemClickListener = AdapterView.OnItemClickListener{adapterView, view, i, l ->
             val itemSelected = adapterView.getItemAtPosition(i).toString()

            val filteredEvents = mockEvents.filter { event ->
                event.category.name == itemSelected
            }
             if(itemSelected == "Svi"){
                 recyclerView = findViewById(R.id.events)
                 recyclerView.adapter = EventAdapter(MockDataLoader.getDemoData())
                 recyclerView.layoutManager = LinearLayoutManager(this)
             }
             if(itemSelected == "Zabavni"){
                 recyclerView.adapter = EventAdapter(filteredEvents)
                 recyclerView.layoutManager = LinearLayoutManager(this)
             }
             if(itemSelected == "Edukativni"){
                 recyclerView.adapter = EventAdapter(filteredEvents)
                 recyclerView.layoutManager = LinearLayoutManager(this)
             }
             if(itemSelected == "Volonterski"){
                 recyclerView.adapter = EventAdapter(filteredEvents)
                 recyclerView.layoutManager = LinearLayoutManager(this)
             }

         }
    }


}