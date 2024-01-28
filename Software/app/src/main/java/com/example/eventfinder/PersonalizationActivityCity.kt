package com.example.eventfinder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eventfinder.Database.DatabaseAPP
import com.example.eventfinder.R

class PersonalizationActivityCity: AppCompatActivity() {
    val TAG="PersonalizationActivityCity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personalization_city)

        val zagrebButton: Button = findViewById(R.id.buttonZagreb)
        val splitButton: Button = findViewById(R.id.buttonSplit)
        val osijekButton: Button = findViewById(R.id.buttonOsijek)
        val rijekaButton: Button = findViewById(R.id.buttonRijeka)
        val userId: Long = intent.getLongExtra("ARGUMENT_KEY", 0)

        zagrebButton.setOnClickListener {
            saveChoice("Zagreb", userId)
            openLogin()
        }

        splitButton.setOnClickListener {
            saveChoice("Split", userId)
            openLogin()
        }

        osijekButton.setOnClickListener {
            saveChoice("Osijek", userId)
            openLogin()
        }

        rijekaButton.setOnClickListener {
            saveChoice("Rijeka", userId)
            openLogin()
        }
    }

    private fun saveChoice(choice: String, id: Long) {
        val user = DatabaseAPP.getInstance().UsersDAO().getUserById(id)
        if (user != null) {
            user.KeyInterestCity = choice
            DatabaseAPP.getInstance().UsersDAO().updateUser(user)
        } else {
            Toast.makeText(this, "No active user found", Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(this, "Chosen City: $choice", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Retrieved user by ID. User: $user");
    }

    fun openLogin(){
        val intent = Intent(this,LoginActivity :: class.java)
        startActivity(intent)
        finish()
    }
}