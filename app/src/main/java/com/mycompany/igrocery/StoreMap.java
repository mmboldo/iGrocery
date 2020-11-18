package com.mycompany.igrocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

        ImageView imageViewMap = findViewById(R.id.storeMapIV);
        seeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedItem = spinner.getSelectedItemPosition();

                if(selectedItem == 0) {
                    imageViewMap.setImageResource(R.drawable.saveonfoodsmap);
                    Toast.makeText(getBaseContext(), "Click on the map to see details", Toast.LENGTH_LONG).show();
                    imageViewMap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            displayFragment();
                        }
                    });
                }
                if(selectedItem == 1) {
                    imageViewMap.setImageResource(R.drawable.walmartmap);
                    Toast.makeText(getApplicationContext(), "Click on the map to see details", Toast.LENGTH_LONG).show();
                }
                if(selectedItem == 2) {
                    imageViewMap.setImageResource(R.drawable.superstoremap);
                    Toast.makeText(getBaseContext(), "Click on the map to see details", Toast.LENGTH_LONG).show();
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

    public void displayFragment() {
        //it is possible to send information from activity to the fragment,
        // send it as a parameter of newInstance()
        StoreMapFragment storeMapFragment = StoreMapFragment.newInstance();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, storeMapFragment).addToBackStack(null).commit();
    }


}