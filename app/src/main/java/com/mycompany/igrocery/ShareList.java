package com.mycompany.igrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class ShareList extends AppCompatActivity {

    DatabaseReference reference;

    Button btnShare;
    EditText sharedName, sharedEmail;
    Integer itemNum = new Random().nextInt();

    //Initialize Drawer Navigation variable
    DrawerLayout drawerLayout;

    //Initialize variables
    RecyclerView sharedList; //ourdoes
    ArrayList<SharedListUser> sharedListUser; //list
    SharedListAdapter sharedListAdapter;


    private FirebaseUser user; //Firebase obj
    private String userEmail;

    public void getCurrentUser() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = user.getEmail().replace(".", "&");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_list);

        sharedName = findViewById(R.id.EditTextSharedName);
        sharedEmail = findViewById(R.id.EditTextSharedEmail);
        btnShare = findViewById(R.id.btn_ShareList);
        sharedListUser = new ArrayList<SharedListUser>();

        getCurrentUser();

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedListUser.clear();
                String sharedEmail1 = sharedEmail.getText().toString().replace(".","&");
                reference = FirebaseDatabase.getInstance().getReference().child("ListsPermissions");
                SharedListUser newShareUser = new SharedListUser(sharedEmail1);
                reference.child(userEmail).child(sharedName.getText().toString()).setValue(newShareUser);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            SharedListUser p = dataSnapshot.getValue(SharedListUser.class);
                            sharedListUser.add(p);
                        }
                        Toast.makeText(ShareList.this, "ShareList Size: " + sharedListUser.size(), Toast.LENGTH_SHORT).show();
                        sharedListAdapter = new SharedListAdapter(ShareList.this, sharedListUser);
                        sharedList.setAdapter(sharedListAdapter);
                        sharedListAdapter.notifyDataSetChanged();
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
        redirectActivity(this, CreateList.class);
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


    public void ClickStoreMap(View view) {
        redirectActivity(this, StoreMap.class);
    }
    //My Shared List for Edurado
    public void ClickMyShared(View view) {
        redirectActivity(this, MySharedListActivity.class);
    }

    // This adds the navigation functionality for the main_nav_drawer Share my list menu link
    public void shareMyList(View view) {
        Intent intent2 = new Intent(ShareList.this, ShareList.class);
        startActivity(intent2);
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
                Intent intent = new Intent(ShareList.this, Login.class);
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