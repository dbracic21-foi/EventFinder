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
import com.example.eventfinder.Database.DatabaseAPP
import com.example.eventfinder.Database.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
        DatabaseAPP.buildInstance(applicationContext)


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

        button!!.setOnClickListener {
            val newUser = User(
                firstName = editTextFirstName!!.text.toString(),
                lastName = editTextLastName!!.text.toString(),
                username = editTextUsername!!.text.toString(),
                pasword = editTextPassword!!.text.toString(),
                Email = editTextEmail!!.text.toString(),
                KeyInterestCategory = "",
                KeyInterestCity = ""
            )
            GlobalScope.launch(Dispatchers.IO) {
                DatabaseAPP.getInstance().UsersDAO().insertUser(newUser)
                println("Korisnik uspjesno registriran : $newUser")
            }
            val userSearch = DatabaseAPP.getInstance().UsersDAO().getLastInsertedUser();
            if(userSearch == null){

            }else{
                openPersonalization(userSearch.id)
            }
        }

        textViewAlreadyLoggedIn!!.setOnClickListener {
            openLogin()
        }
    }

    private fun validateFields() {
        val allFieldsFilled = !editTextFirstName!!.text.isNullOrBlank() &&
                !editTextLastName!!.text.isNullOrBlank() &&
                !editTextUsername!!.text.isNullOrBlank() &&
                !editTextPassword!!.text.isNullOrBlank() &&
                !editTextEmail!!.text.isNullOrBlank()

        button!!.isEnabled = allFieldsFilled
    }

    private fun openLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openPersonalization(id_user: Long) {
        val intent = Intent(this, PersonallizationActivity::class.java)
        intent.putExtra("ARGUMENT_KEY", id_user)
        startActivity(intent)
        finish()
    }
}
