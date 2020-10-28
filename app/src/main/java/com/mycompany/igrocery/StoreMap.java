package com.mycompany.igrocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoreMap extends AppCompatActivity {

    List<String> StoreNames = new ArrayList<>(Arrays.asList("Choose a Store", "Save On Foods", "Walmart", "Whole Foods"));
    List<Integer> StorePics = new ArrayList<>(Arrays.asList(R.drawable.saveonfoods,R.drawable.saveonfoods, R.drawable.saveonfoods,
            R.drawable.saveonfoods));

    //list of names and pictures combined
    List<Stores> EmptyStoreList = new ArrayList<>();
    List<Stores> AllStores = new ArrayList<>();
    List<Stores> SaveOnFoods = new ArrayList<>();
    List<Stores> WalmartStore = new ArrayList<>();
    List<Stores> WholeFoods = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddData();

        Spinner spinner = findViewById(R.id.storesSpinner);
        ArrayAdapter<Stores> adapter = new ArrayAdapter<Stores>(this, android.R.layout.simple_spinner_item, AllStores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final RecyclerView recyclerView = findViewById(R.id.recycleViewMap);

        //set the grid of the RecycleView
        GridLayoutManager gm = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gm);
        final StoresAdapter myStoreAdapter = new StoresAdapter(EmptyStoreList, this);
        recyclerView.setAdapter(myStoreAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (spinner.getSelectedItemPosition()) {
                    case 0:
                        break;
                    case 1:
                        myStoreAdapter.ChangeData(SaveOnFoods);
                        break;
                    case 2:
                        myStoreAdapter.ChangeData(WalmartStore);
                        break;
                    case 3:
                        myStoreAdapter.ChangeData(WholeFoods);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //method to add name and picture into arraylist
    private void AddData() {
        for(int i = 0; i < StoreNames.size(); i++) {
            Stores eachStore = new Stores(StoreNames.get(i), StorePics.get(i));
            AllStores.add(eachStore);
        }
        SaveOnFoods = AllStores.subList(1, 2);
        WalmartStore = AllStores.subList(2, 3);
        WholeFoods = AllStores.subList(3, 4);

    }
}