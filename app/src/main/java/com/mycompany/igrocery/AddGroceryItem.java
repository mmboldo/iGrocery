package com.mycompany.igrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import static androidx.core.content.ContextCompat.startActivity;

public class AddGroceryItem extends AppCompatActivity {

    EditText addItemTitle, addItemDescription, addItemQuantity;
    Button btnSave, btnCancel;
    DatabaseReference reference;
    Integer itemNum = new Random().nextInt();
    String itemKey = Integer.toString(itemNum);

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseUser user; //Firebase obj
    private String userId, eventName;

    public void getCurrentUser() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery_item);

        addItemTitle = findViewById(R.id.addItemTitle);
        addItemDescription = findViewById(R.id.addItemDescription);
        addItemQuantity = findViewById(R.id.addItemQuantity);

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        getCurrentUser();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // insert data to database
                reference = FirebaseDatabase.getInstance().getReference().child(("GroceryList")).child("userId: " + userId).child("GroceryItem"+itemNum);

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        reference.child("itemTitle").setValue(addItemTitle.getText().toString());
                        reference.child("itemDescription").setValue(addItemDescription.getText().toString());
                        reference.child("itemQuantity").setValue(addItemQuantity.getText().toString());
                        reference.child("itemKey").setValue(itemKey);

                        Intent intent = new Intent(AddGroceryItem.this, CreateList.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddGroceryItem.this, CreateList.class);
                startActivity(intent);
            }
        });
    }
}