package com.example.eventfinder
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventfinder.adapters.ReviewAdapter
import com.example.eventfinder.Database.DatabaseAPP
import com.example.eventfinder.entities.Review

class ReviewActivity : AppCompatActivity() {

    private lateinit var reviewAdapter: ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewReviews)
        recyclerView.layoutManager = LinearLayoutManager(this)

        reviewAdapter = ReviewAdapter(emptyList())
        recyclerView.adapter = reviewAdapter

        val eventId = intent.getIntExtra("eventId", -1)


        if (eventId != -1) {
            loadReviewsForEvent(eventId)
        } else {
        }

        val povratakButton: Button = findViewById(R.id.povratak)
        povratakButton.setOnClickListener {
            openViewerActivity()
        }

        val newrecenzijaButton: Button = findViewById(R.id.novarecenzija)
        newrecenzijaButton.setOnClickListener {
            openNewViewerActivity()
        }
    }


    private fun loadReviewsForEvent(eventId: Int) {
        val reviewDao = DatabaseAPP.getInstance().getReviewsDao()
        val reviewsForEvent = reviewDao.getReviewsForEvent(eventId)

        Log.d("ReviewActivity", "Broj recenzija prije čišćenja: ${reviewAdapter.itemCount}")

        Log.d("ReviewActivity", "Pronađene recenzije iz baze: $reviewsForEvent")

        reviewAdapter.clearData()

        // Provjera unikatnosti recenzija prije dodavanja
        val uniqueReviews = reviewsForEvent.distinctBy { it.id }
        reviewAdapter.setData(uniqueReviews)

        Log.d("ReviewActivity", "Broj recenzija nakon dodavanja: ${reviewAdapter.itemCount}")
    }



    private fun openViewerActivity() {
        val intent = Intent(this, ViewerActivity::class.java)
        startActivity(intent)
    }

    private fun openNewViewerActivity() {
        val eventId = intent.getIntExtra("eventId", -1)
        Log.d("nesto12","nesto13123: $eventId")
        val intent = Intent(this, NewReviewActivity::class.java)
        intent.putExtra("eventId",eventId)
        startActivity(intent)
    }




}