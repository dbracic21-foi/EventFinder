package com.example.eventfinder

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    private var textviewNeedAccount: TextView? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        textviewNeedAccount = findViewById<View>(R.id.textviewNeedAccount) as TextView
        textviewNeedAccount!!.setOnClickListener{
            openRegisterForm()
        }
    }
    fun openRegisterForm(){
        var intent = Intent(this, RegistrationActivity ::class.java)
        startActivity(intent)
        finish()
    }
}