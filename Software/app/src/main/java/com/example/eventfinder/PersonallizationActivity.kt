package com.example.eventfinder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eventfinder.Database.DatabaseAPP
import com.example.eventfinder.Database.User
import com.example.eventfinder.R

class PersonallizationActivity : AppCompatActivity() {
    val TAG = "PersonallizationActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personalization_categories)
        val userId: Long = intent.getLongExtra("ARGUMENT_KEY", 0)

        val zabavniButton: Button = findViewById(R.id.buttonZabavni)
        val edukativniButton: Button = findViewById(R.id.buttonEdukativni)
        val volonterskiButton: Button = findViewById(R.id.buttonVolonterski)

        zabavniButton.setOnClickListener {
            saveChoice("Zabavni", userId)
            openPersonalization(userId)
        }

        edukativniButton.setOnClickListener {
            saveChoice("Edukativni", userId)
            openPersonalization(userId)
        }

        volonterskiButton.setOnClickListener {
            saveChoice("Volonterski", userId)
            openPersonalization(userId)
        }
    }

    private fun saveChoice(choice: String, id: Long) {
        val user = DatabaseAPP.getInstance().UsersDAO().getUserById(id);
        if(user != null){
            user.KeyInterestCategory = choice;
            DatabaseAPP.getInstance().UsersDAO().updateUser(user);
        } else {
            Toast.makeText(this, "No active user found", Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(this, "Chosen Category: $choice", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Retrieved user by ID. User: $user");
    }

    fun openPersonalization(id_user: Long){
        val intent = Intent(this,PersonalizationActivityCity :: class.java)
        intent.putExtra("ARGUMENT_KEY", id_user)
        startActivity(intent)
        finish()
    }
}
