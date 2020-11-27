package com.mycompany.igrocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MySharedListActivity extends AppCompatActivity {
    //Initialize Drawer Navigation variable
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shared_list);

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
        recreate();
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
        redirectActivity(this, StoreMap.class);
    }

    // This adds the navigation functionality for the main_nav_drawer Share my list menu link
    public void shareMyList(View view) {
        Intent intent2 = new Intent(MySharedListActivity.this, ShareList.class);
        startActivity(intent2);
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
                Intent intent = new Intent(MySharedListActivity.this, Login.class);
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