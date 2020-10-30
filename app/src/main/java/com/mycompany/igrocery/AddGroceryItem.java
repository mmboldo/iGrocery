package com.mycompany.igrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class AddGroceryItem extends AppCompatActivity {

    EditText addItemTitle, addItemDescription, addItemQuantity;
    Button btnSave, btnCancel;
    DatabaseReference reference;
    Integer itemNum = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery_item);

        addItemTitle = findViewById(R.id.addItemTitle);
        addItemDescription = findViewById(R.id.addItemDescription);
        addItemQuantity = findViewById(R.id.addItemQuantity);

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // insert data to database
                reference = FirebaseDatabase.getInstance().getReference().child(("GroceryList")).child("GroceryItem"+itemNum);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("itemTitle").setValue(addItemTitle.getText().toString());
                        snapshot.getRef().child("itemDescription").setValue(addItemDescription.getText().toString());
                        snapshot.getRef().child("itemQuantity").setValue(addItemQuantity.getText().toString());

                        Intent intent = new Intent(AddGroceryItem.this, CreateList.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}