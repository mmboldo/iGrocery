package com.mycompany.igrocery;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

public class StoreMap extends AppCompatActivity {

    Button storeMapFreshBtn, storeMapGroceryBtn, storeMapCleaningBtn, storeMapPetsBtn, storeMapEletroBtn,
            storeMapToysBtn, storeMapBakeryBtn, storeMapApparelBtn, storeMapOfficeBtn, storeMapPartyBtn,
            storeMapHomeBtn, storeMapAutoBtn, storeMapSportBtn, storeMapCosmBtn, storeMapHealthBtn,
            storeMapPharmBtn, storeMapCheckOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_map);
        //id each btn
        storeMapBakeryBtn = (Button) findViewById(R.id.storeMapBakeryBtn);
        storeMapFreshBtn = (Button) findViewById(R.id.storeMapFreshBtn);
        storeMapGroceryBtn = (Button) findViewById(R.id.storeMapGroceryBtn);
        storeMapCleaningBtn = (Button) findViewById(R.id.storeMapCleaningBtn);
        storeMapPetsBtn = (Button) findViewById(R.id.storeMapPetBtn);
        storeMapEletroBtn = (Button) findViewById(R.id.storeMapEletroBtn);
        storeMapToysBtn = (Button) findViewById(R.id.storeMapToysBtn);
        storeMapApparelBtn = (Button) findViewById(R.id.storeMapApparelBtn);
        storeMapOfficeBtn = (Button) findViewById(R.id.storeMapOfficeBtn);
        storeMapPartyBtn = (Button) findViewById(R.id.storeMapPartyBtn);
        storeMapHomeBtn = (Button) findViewById(R.id.storeMapHomeBtn);
        storeMapAutoBtn = (Button) findViewById(R.id.storeMapAutoBtn);
        storeMapSportBtn = (Button) findViewById(R.id.storeMapSportBtn);
        storeMapCosmBtn = (Button) findViewById(R.id.storeMapCosmBtn);
        storeMapHealthBtn = (Button) findViewById(R.id.storeMapHealthBtn);
        storeMapPharmBtn = (Button) findViewById(R.id.storeMapPharmBtn);
        storeMapCheckOutBtn = (Button) findViewById(R.id.storeMapCheckOutBtn);

        //set btn colors
        storeMapBakeryBtn.setBackgroundColor(Color.parseColor("#999900"));
        storeMapFreshBtn.setBackgroundColor(Color.parseColor("#00FF7F"));
        storeMapGroceryBtn.setBackgroundColor(Color.parseColor("#228B22"));
        storeMapCleaningBtn.setBackgroundColor(Color.parseColor("#87CEFA"));
        storeMapPetsBtn.setBackgroundColor((Color.parseColor("#A52A2A")));
        storeMapEletroBtn.setBackgroundColor((Color.parseColor("#1E90FF")));
        storeMapToysBtn.setBackgroundColor((Color.parseColor("#FF1493")));
        storeMapApparelBtn.setBackgroundColor((Color.parseColor("#1E90FF")));
        storeMapOfficeBtn.setBackgroundColor((Color.parseColor("#6495ED")));
        storeMapPartyBtn.setBackgroundColor((Color.parseColor("#4682B4")));
        storeMapHomeBtn.setBackgroundColor((Color.parseColor("#4169E1")));
        storeMapAutoBtn.setBackgroundColor((Color.parseColor("#5F9EA0")));
        storeMapSportBtn.setBackgroundColor((Color.parseColor("#20B2AA")));
        storeMapCosmBtn.setBackgroundColor((Color.parseColor("#FF7F50")));
        storeMapHealthBtn.setBackgroundColor((Color.parseColor("#FF6347")));
        storeMapPharmBtn.setBackgroundColor((Color.parseColor("#DDA0DD")));
        storeMapCheckOutBtn.setBackgroundColor((Color.parseColor("#A9A9A9")));
    }
}