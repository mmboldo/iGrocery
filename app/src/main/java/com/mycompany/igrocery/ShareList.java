package com.mycompany.igrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
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

public class ShareList extends AppCompatActivity {

    DatabaseReference reference;

    Button btnShare;
    EditText sharedName, sharedEmail;
    Integer itemNum = new Random().nextInt();

    //Initialize Drawer Navigation variable
    DrawerLayout drawerLayout;

    private FirebaseUser user; //Firebase obj
    private String userEmail;

    public void getCurrentUser() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = user.getEmail();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_list);

        // sharedName = findViewById(R.id.EditTextSharedName);
        sharedEmail = findViewById(R.id.EditTextSharedEmail);
        btnShare = findViewById(R.id.btn_ShareList);

        getCurrentUser();

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sharedEmail1 = sharedEmail.getText().toString().replace(".","&");
                reference = FirebaseDatabase.getInstance().getReference().child(("ListsPermissions")).child("userEmail: " + sharedEmail1).child("Invitations"+itemNum);
                reference.child("listOwner").setValue(userEmail);

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Intent intent = new Intent(ShareList.this, CreateList.class);
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