package com.example.eventfinder

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RegistrationActivity : AppCompatActivity() {
    private var button: Button? = null
    private var textViewAlreadyLoggedIn: TextView? = null
    private var editTextFirstName: EditText? = null
    private var editTextLastName: EditText? = null
    private var editTextUsername: EditText? = null
    private var editTextPassword: EditText? = null
    private var editTextEmail: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        button = findViewById<View>(R.id.buttonRegister) as Button
        textViewAlreadyLoggedIn = findViewById<View>(R.id.textViewAlreadyLoggedIn) as TextView
        editTextFirstName = findViewById<View>(R.id.editTextFirstName) as EditText
        editTextLastName = findViewById<View>(R.id.editTextLastName) as EditText
        editTextUsername = findViewById<View>(R.id.editTextUsername) as EditText
        editTextPassword = findViewById<View>(R.id.editTextPassword) as EditText
        editTextEmail = findViewById<View>(R.id.editTextEmail) as EditText
        button!!.isEnabled = false

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Poziva se kad se promijeni tekst u bilo kojem EditText polju
                validateFields()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        // Dodajemo TextWatcher na svako EditText polje
        editTextFirstName!!.addTextChangedListener(textWatcher)
        editTextLastName!!.addTextChangedListener(textWatcher)
        editTextUsername!!.addTextChangedListener(textWatcher)
        editTextPassword!!.addTextChangedListener(textWatcher)
        editTextEmail!!.addTextChangedListener(textWatcher)

        button!!.setOnClickListener { openLogin() }

        textViewAlreadyLoggedIn!!.setOnClickListener {
            openLogin()
        }
    }

    private fun validateFields() {
        // Provjeravamo jesu li sva polja ispunjena
        val allFieldsFilled = !editTextFirstName!!.text.isNullOrBlank() &&
                !editTextLastName!!.text.isNullOrBlank() &&
                !editTextUsername!!.text.isNullOrBlank() &&
                !editTextPassword!!.text.isNullOrBlank() &&
                !editTextEmail!!.text.isNullOrBlank()

        // Postavljamo stanje gumba ovisno o rezultatu provjere
        button!!.isEnabled = allFieldsFilled
    }

    private fun openLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}