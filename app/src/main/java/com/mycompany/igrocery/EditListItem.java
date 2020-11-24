package com.mycompany.igrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditListItem extends AppCompatActivity {

    EditText editItemTitle, editItemDescription, editItemQuantity;
    Button btnSaveUpdate, btnDelete;
    DatabaseReference mDatabaseReference;

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseUser user; //Firebase obj
    private String userEmail, eventName;

    public void getCurrentUser() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = user.getEmail().replace(".", "&");    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list_item);

        editItemTitle = findViewById(R.id.editItemTitle);
        editItemDescription = findViewById(R.id.editItemDescription);
        editItemQuantity = findViewById(R.id.editItemQuantity);

        btnSaveUpdate = findViewById(R.id.btnSaveUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        editItemTitle.setText(getIntent().getStringExtra("itemTitle"));
        editItemDescription.setText(getIntent().getStringExtra("itemDescription"));
        editItemQuantity.setText(getIntent().getStringExtra("itemQuantity"));
        final String key = getIntent().getStringExtra("itemKey");
        getCurrentUser();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(("GroceryList")).child("userEmail: " + userEmail).child("GroceryItem"+key);

                mDatabaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent a = new Intent(EditListItem.this, CreateList.class);
                        startActivity(a);
                        finish();
                    }
                });
            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(("GroceryList")).child("userEmail: " + userEmail).child("GroceryItem"+key);

                mDatabaseReference.child("itemTitle").setValue(editItemTitle.getText().toString());
                mDatabaseReference.child("itemDescription").setValue(editItemDescription.getText().toString());
                mDatabaseReference.child("itemQuantity").setValue(editItemQuantity.getText().toString());
                mDatabaseReference.child("itemKey").setValue(key);

                mDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Intent intent = new Intent(EditListItem.this, CreateList.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }
}