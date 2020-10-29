package com.mycompany.igrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateList extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView groceryList;
    ArrayList<GroceryList> list;
    GroceryListAdapter groceryListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        // working with dat
        groceryList = findViewById(R.id.groceryList);
        groceryList.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<GroceryList>();

        // getting data from firebase
        reference = FirebaseDatabase.getInstance().getReference().child("iGrocery");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // retrieve data and create layout
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
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