package com.example.eventfinder

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
class RegistrationActivity : AppCompatActivity() {
    private var button: Button? = null
    private var textViewAlreadyLoggedIn: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        button = findViewById<View>(R.id.buttonRegister) as Button
        textViewAlreadyLoggedIn = findViewById<View>(R.id.textViewAlreadyLoggedIn) as TextView

        button!!.setOnClickListener { openLogin() }

        textViewAlreadyLoggedIn!!.setOnClickListener {
             openLogin()
        }
    }

    fun openLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
