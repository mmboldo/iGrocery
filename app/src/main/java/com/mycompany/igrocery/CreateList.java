package com.mycompany.igrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateList extends AppCompatActivity {

    Button btnAddNew;

    DatabaseReference reference;
    RecyclerView groceryList;
    ArrayList<GroceryList> list;
    GroceryListAdapter groceryListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        btnAddNew = findViewById(R.id.btnAddNew);

        btnAddNew.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateList.this, AddGroceryItem.class);
                startActivity(intent);
            }
        });

        // working with data
        groceryList = findViewById(R.id.groceryList);
        groceryList.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<GroceryList>();

        // getting data from firebase
        reference = FirebaseDatabase.getInstance().getReference().child("GroceryList");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // retrieve data and create layout
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    GroceryList p = dataSnapshot.getValue(GroceryList.class);
                    list.add(p);
                }

                groceryListAdapter = new GroceryListAdapter(CreateList.this, list);
                groceryList.setAdapter(groceryListAdapter);
                groceryListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Error
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}