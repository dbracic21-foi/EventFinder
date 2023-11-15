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

class LoginActivity : AppCompatActivity() {
    private var ButtonLogin : Button? = null
    private var textviewNeedAccount: TextView? = null
    private var editTextUserNameOrEmail : EditText? = null
    private var editTextPassword : EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ButtonLogin!!.setOnClickListener { View.OnClickListener{startActivity(Intent(this, ViewerActivity::class.java))} }

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

        textviewNeedAccount!!.setOnClickListener{
            openRegisterForm()
        }
    }
    private fun validateFields(){
        val allFieldsFilled = !editTextUserNameOrEmail!!.text.isNullOrBlank() && !editTextPassword!!.text.isNullOrBlank()
        ButtonLogin!!.isEnabled = allFieldsFilled
    }
    fun openRegisterForm(){
        var intent = Intent(this, RegistrationActivity :: class.java)
        startActivity(intent)
        finish()
    }
}