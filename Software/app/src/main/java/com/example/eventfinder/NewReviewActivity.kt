package com.example.eventfinder
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import com.example.eventfinder.Database.DatabaseAPP
import com.example.eventfinder.entities.Review

class NewReviewActivity : AppCompatActivity() {

    private lateinit var editTextComment: EditText
    private lateinit var ratingBar: RatingBar
    private lateinit var buttonAddComment: Button
    private lateinit var buttonCancel: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_review)
       val eventId = intent.getIntExtra("eventId",-1)
        Log.d("Id eventa je","Id eventa je : $eventId")
        editTextComment = findViewById(R.id.editTextComment)
        ratingBar = findViewById(R.id.ratingBar)
        buttonAddComment = findViewById(R.id.buttonAddComment)
        buttonCancel = findViewById(R.id.buttonCancel)

        buttonAddComment.setOnClickListener {
            dodajKomentar()
        }

        buttonCancel.setOnClickListener {
            val intent = Intent(this, ReviewActivity::class.java)
            startActivity(intent)

            finish()
        }
    }

    private fun dodajKomentar() {
        val komentar = editTextComment.text.toString()
        val ocjena = ratingBar.rating.toFloat()
        val eventId = intent.getIntExtra("eventId",-1)
        if (eventId != -1) {
            val review = Review(eventID = eventId, rating = ocjena, comment = komentar)
            Log.d("NewReviewActivity", "eventID: $eventId, ocjena: $ocjena, komentar: $komentar")
            val reviewDao = DatabaseAPP.getInstance().getReviewsDao()
            reviewDao.insertReview(review)

        }

        finish()
    }

}
