package com.example.eventfinder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventfinder.adapters.ReviewAdapter
import com.example.eventfinder.entities.Review

class ReviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)

        val reviews = listOf(
            Review(1, 1, 4.50f, "Odličan događaj!"),
            Review(2, 2, 3.0f, "Solidno, ali moglo je bolje."),
            Review(3, 1, 5.0f, "Fantastično iskustvo!")
        )

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewReviews)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = ReviewAdapter(reviews)

        val povratakButton: Button = findViewById(R.id.povratak)
        povratakButton.setOnClickListener {
            openViewerActivity()
        }
        val newrecenzijaButton: Button = findViewById(R.id.novarecenzija)
        newrecenzijaButton.setOnClickListener {
            openNewViewerActivity()
        }

    }

    private fun openViewerActivity() {
        val intent = Intent(this, ViewerActivity::class.java)
        startActivity(intent)
    }
    private fun openNewViewerActivity() {
        val intent = Intent(this, NewReviewActivity::class.java)
        startActivity(intent)
    }
}
