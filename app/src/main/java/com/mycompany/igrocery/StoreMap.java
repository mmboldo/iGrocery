package com.mycompany.igrocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.mycompany.igrocery.R.id.storesSpinner;

public class StoreMap extends AppCompatActivity {

    List<Stores> StoreList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_map);

        StoreList = ReadFile();
        Log.d("FILELOG", StoreList.size() + " stores on the file.");

        Spinner spinner = findViewById(R.id.storesSpinner);
        spinner.setAdapter(new StoresAdapter(StoreList));

        Button seeMap = (Button) findViewById(R.id.seeMapStoreBtn);
        seeMap.setBackgroundColor(Color.parseColor("#66CD00"));
        seeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedItem = spinner.getSelectedItemPosition();

                ImageView imageViewMap = findViewById(R.id.storeMapIV);

                if(selectedItem == 0) {
                    imageViewMap.setImageResource(R.drawable.saveonfoodsmap);
                }
                if(selectedItem == 1) {
                    imageViewMap.setImageResource(R.drawable.walmartmap);
                }
                if(selectedItem == 2) {
                    imageViewMap.setImageResource(R.drawable.superstoremap);
                }
            }
        });

    }

    private List<Stores> ReadFile() {
        List<Stores> ListOfStores = new ArrayList<>();

        InputStream inputStream = getResources().openRawResource(R.raw.storeinfo);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String csvLine;
            if((csvLine = reader.readLine()) != null) {

            }
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                int id = Integer.parseInt(row[0]);
                String storePicName = row[1];
                int storeDrawable = getResources().getIdentifier(storePicName, "drawable", getPackageName());
                String storeName = row[2];
                String storeAddress = row[3];
                String storeMapPicName = row[4];
                int storeMapDrawable = getResources().getIdentifier(storeMapPicName, "drawable", getPackageName());

                Stores eachStore = new Stores(id, storePicName, storeDrawable, storeName, storeAddress, storeMapPicName, storeMapDrawable);
                ListOfStores.add(eachStore);
            }
        } catch (Exception ex) {
            Log.d("FILELOG", ex.getMessage() + "File Process error");
        }
        return ListOfStores;
    }
}