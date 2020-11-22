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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditListItem extends AppCompatActivity {

    EditText editItemTitle, editItemDescription, editItemQuantity;
    Button btnSaveUpdate, btnDelete;
    DatabaseReference reference;

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

        reference = FirebaseDatabase.getInstance().getReference().child(("GroceryList")).child("GroceryItem"+key);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue();
                Intent a = new Intent(EditListItem.this, CreateList.class);
                startActivity(a);


//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Intent a = new Intent(EditListItem.this, CreateList.class);
//                        startActivity(a);
//                    }
//                });
            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reference.child("itemTitle").setValue(editItemTitle.getText().toString());
                        reference.child("itemDescription").setValue(editItemDescription.getText().toString());
                        reference.child("itemQuantity").setValue(editItemQuantity.getText().toString());
                        reference.child("itemKey").setValue(key);

                        Intent intent = new Intent(EditListItem.this, CreateList.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                reference.removeValue();
            }
        });
    }
}