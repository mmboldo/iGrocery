package com.mycompany.igrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SharedList extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView sharedList; //ourdoes
    ArrayList<SharedListUser> sharedListUser; //list
    SharedListAdapter sharedListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_list);

        //working with data
        sharedList = findViewById(R.id.rvSharedList);
        sharedList.setLayoutManager(new LinearLayoutManager(this));
        sharedListUser = new ArrayList<SharedListUser>();

        //get data from firebase
        reference = FirebaseDatabase.getInstance().getReference().child("ListPermissions");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //set code to retrieve data and replace layout
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    SharedListUser p = dataSnapshot.getValue(SharedListUser.class);
                    sharedListUser.add(p);
                }
                sharedListAdapter = new SharedListAdapter(SharedList.this, sharedListUser);
                sharedList.setAdapter(sharedListAdapter);
                sharedListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //set code to show an error
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();
            }
        });

    }
}