package com.example.eventfinder

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RedirectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val urlOrganizer = intent.getStringExtra("urlOrganizer")

        if (!urlOrganizer.isNullOrBlank()) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(urlOrganizer))
            startActivity(browserIntent)
        }

        finish()
    }
}
