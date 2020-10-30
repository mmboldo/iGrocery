package com.mycompany.igrocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class StoreSelection extends AppCompatActivity {

    RecyclerView recyclerView;
    String s1[];
    int images[] = {R.drawable.superstore_logo, R.drawable.saveonfoods_logo, R.drawable.walmart_logo};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_selection);

        recyclerView = findViewById(R.id.rvStoreSelection);

        s1 = getResources().getStringArray(R.array.store_names);

        StoreSelectionAdapter adapter = new StoreSelectionAdapter(this, s1, images);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}