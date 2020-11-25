package com.mycompany.igrocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ShareListInput extends AppCompatActivity {

    EditText share;
    Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_list_input);

        share = findViewById(R.id.etShareListId);
        btnShare = findViewById(R.id.btnShareListId);


        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ShareListInput.this, CreateList.class);
                intent.putExtra("userEmail", share.getText().toString());

                //Toast.makeText(ShareListInput.this, share.getText().toString(), Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
    }
}