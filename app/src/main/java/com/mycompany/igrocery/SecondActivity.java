package com.mycompany.igrocery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);

        TextView textViewStoreName = findViewById(R.id.storeNameTV);
        TextView textViewStoreAddress = findViewById(R.id.addressTV);
        ImageView imageViewMap = findViewById(R.id.mapIV);

        if(getIntent().getExtras() != null) {
            int storeMap = getIntent().getExtras().getInt("STOREMAPPIC");
            String storeName = getIntent().getExtras().getString("NAME");
            String storeAddress = getIntent().getExtras().getString("ADDRESS");
            textViewStoreName.setText(storeName);
            textViewStoreAddress.setText(storeAddress);
            imageViewMap.setImageResource(storeMap);
        }
    }
}