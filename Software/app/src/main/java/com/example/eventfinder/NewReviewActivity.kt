package com.example.eventfinder
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity

class NewReviewActivity : AppCompatActivity() {

    private lateinit var editTextComment: EditText
    private lateinit var ratingBar: RatingBar
    private lateinit var buttonAddComment: Button
    private lateinit var buttonCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_review)

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
        val ocjena = ratingBar.rating

        // Implementirajte ovdje logiku za dodavanje komentara (npr. spremanje u bazu podataka)

        // Zatvori aktivnost
        finish()
    }
}
