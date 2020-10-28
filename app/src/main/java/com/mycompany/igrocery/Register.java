package com.mycompany.igrocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Register extends AppCompatActivity {
    Button alreadyUserBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //find the id
        alreadyUserBtn = (Button) findViewById(R.id.alreadyUserBtn);

        //Go to Login
        alreadyUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Register.this, Login.class);
                startActivity(myIntent);
            }
        });
    }
}