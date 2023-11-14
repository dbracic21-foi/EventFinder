package com.example.eventfinder;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
public class RegistrationActivity extends AppCompatActivity {
private  Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        button = (Button) findViewById(R.id.buttonRegister);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });


    }
    public void openLogin() {


        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }
}