package com.mycompany.igrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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

    //private FirebaseAuth.AuthStateListener authListener;
    private FirebaseUser user; //Firebase obj
    private String userEmail, eventName;

    //Initialize Drawer Navigation variable
    DrawerLayout drawerLayout;

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
        //Toast.makeText(this, userEmail, Toast.LENGTH_SHORT).show();
    }

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
                        a.putExtra("listOwner", userEmail);
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
                        intent.putExtra("listOwner", userEmail);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        //Drawer Navigation
        drawerLayout = findViewById(R.id.drawer_layout);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

    }


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

    public void ClickCalendar(View view) {
        redirectActivity(this, Calendar.class);
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
                Intent intent = new Intent(EditListItem.this, Login.class);
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

    //toolbar settings


}