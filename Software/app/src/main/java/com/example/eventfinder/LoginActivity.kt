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
import androidx.room.Room
import com.example.eventfinder.Database.DatabaseAPP
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private var ButtonLogin : Button? = null
    private var textviewNeedAccount: TextView? = null
    private var editTextUserNameOrEmail : EditText? = null
    private var editTextPassword : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButtonLogin = findViewById<View>(R.id.buttonLogin) as Button
        textviewNeedAccount = findViewById<View>(R.id.textviewNeedAccount) as TextView
        editTextUserNameOrEmail = findViewById<View>(R.id.editTextUsernameOrEmail) as EditText
        editTextPassword = findViewById<View>(R.id.editTextPasswordLogin) as EditText
        ButtonLogin!!.isEnabled = false
        val textWatcher = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateFields()
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        editTextUserNameOrEmail!!.addTextChangedListener(textWatcher)
        editTextPassword!!.addTextChangedListener(textWatcher)

        ButtonLogin!!.setOnClickListener{
            loginUser()
        }

        textviewNeedAccount!!.setOnClickListener{
            openRegisterForm()
        }
    }
     fun showErrorMessage(message : String){
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_INDEFINITE
        )
        
         snackbar.setAction("Zatvori"){
             snackbar.dismiss()
         }
         snackbar.show()
    }

    private fun validateFields(){
        val allFieldsFilled = !editTextUserNameOrEmail!!.text.isNullOrBlank() && !editTextPassword!!.text.isNullOrBlank()
        ButtonLogin!!.isEnabled = allFieldsFilled
    }
    fun openRegisterForm(){
        val intent = Intent(this, RegistrationActivity :: class.java)
        startActivity(intent)
        finish()
    }
    fun openMainActivity(){
        val intent = Intent(this,MapsFragment :: class.java)
        startActivity(intent)
        finish()
    }
    fun loginUser(){
        val  usernameOrEmail = editTextUserNameOrEmail!!.text.toString()
        val password = editTextPassword!!.text.toString()
        val database  = Room.databaseBuilder(applicationContext, DatabaseAPP :: class.java, "App").build()

        GlobalScope.launch (Dispatchers.IO){
            val userDAO = database.UsersDAO()
            val  user = userDAO.getUserByUsernameAndPassword(usernameOrEmail,password)
            launch (Dispatchers.Main){
                if(user != null){
                openMainActivity()
            }   else{
                showErrorMessage("Krivo ste unijeli korisnicko ime ili lozinku")
                println("Pogresno korisnicko ime ili lozinka")
            }  }

        }


    }
}