package com.mycompany.igrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    //Initialize Drawer Navigation variable
    DrawerLayout drawerLayout;

    private FirebaseUser user; //Firebase obj
    private String userEmail;

    public void getCurrentUser() {

        //getting intent
        String listOwner = getIntent().getStringExtra("listOwner");

        if(listOwner != null){
            userEmail = listOwner.replace(".", "&");
        }
        else{
            user = FirebaseAuth.getInstance().getCurrentUser();
            userEmail = user.getEmail().replace(".", "&");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        btnAddNew = findViewById(R.id.btnAddNew);
        getCurrentUser();
        btnAddNew.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateList.this, AddGroceryItem.class);
                intent.putExtra("listOwner", userEmail);
                startActivity(intent);
            }
        });

        // working with data
        groceryList = findViewById(R.id.groceryList);
        groceryList.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<GroceryList>();

        // getting data from firebase
        reference = FirebaseDatabase.getInstance().getReference().child("GroceryList").child("userEmail: " + userEmail);
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // retrieve data and create layout
                list.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    GroceryList p = dataSnapshot.getValue(GroceryList.class);
                    list.add(p);
                }

                groceryListAdapter = new GroceryListAdapter(CreateList.this, list);

                groceryList.refreshDrawableState();
                groceryList.setAdapter(groceryListAdapter);
                groceryListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Error
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });



        //Drawer Navigation
        drawerLayout = findViewById(R.id.drawer_layout);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
    }

    // START: Methods for Nav menu
    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickList(View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = user.getEmail().replace(".", "&");
        Intent intent = new Intent(this, CreateList.class);
        intent.putExtra("listOwner", userEmail);
        startActivity(intent);
        //redirectActivity(this, CreateList.class);
    }

    public void ClickSearchStore(View view){
        redirectActivity(this, PermissionActivity.class);
    }
    public void ClickLogout(View view){
        logout(this);
    }

    public void ClickCalendar(View view) {
        redirectActivity(this, Calendar.class);
    }
    //My Shared List for Edurado
    public void ClickMyShared(View view) {
        redirectActivity(this, MySharedListActivity.class);
    }

    // This adds the navigation functionality for the main_nav_drawer Share my list menu link
    public void shareMyList(View view) {
        redirectActivity(this, ShareList.class);
    }

    public void ClickStoreMap(View view) {
        redirectActivity(this, StoreMap.class);
    }

    public void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //quit the iGrocery app
                /*activity.finishAffinity();
                System.exit(0);*/

                //Back to login page
                Intent intent = new Intent(CreateList.this, Login.class);
                activity.startActivity(intent);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aclass) {
        Intent intent = new Intent(activity, aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //close drawer
        closeDrawer(drawerLayout);
    }
    // FINISH: Methods for Nav menu

}