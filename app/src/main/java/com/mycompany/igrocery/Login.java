package com.mycompany.igrocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Login extends AppCompatActivity {
    Button loginBtn, newUserBtn, goToStoreMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //find the ids
        loginBtn = (Button)findViewById(R.id.loginBtn);
        newUserBtn = (Button) findViewById(R.id.newUserBtn);
        goToStoreMap = (Button) findViewById(R.id.tempBtn);

        //Go to new User Registration
        newUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Login.this, Register.class);
                startActivity(myIntent);
            }
        });
        //Go to Store Map
        goToStoreMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Login.this, StoreMap.class);
                startActivity(myIntent);
            }
        });

    }
}