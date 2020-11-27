package com.mycompany.igrocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StoreMap extends AppCompatActivity {

    List<Stores> StoreList = new ArrayList<>();
    private boolean isFragmentDisplayed = false;
    static final String STATE_FRAGMENT = "state_of_fragment";
    //Initialize Drawer Navigation variable
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_map);

        StoreList = ReadFile();
        Log.d("FILELOG", StoreList.size() + " stores on the file.");

        Spinner spinner = findViewById(R.id.storesSpinner);
        spinner.setAdapter(new StoresAdapter(StoreList));
        ImageView imageViewMap = findViewById(R.id.storeMapIV);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedItem = spinner.getSelectedItemPosition();

                if(selectedItem == 0) {
                    imageViewMap.setImageResource(R.drawable.saveonfoodsmap);
                    Toast.makeText(getBaseContext(), "Click on the map to see details", Toast.LENGTH_LONG).show();
                    imageViewMap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(isFragmentDisplayed) {
                                closeFragment();

                            } else {
                                displayFragment();
                            }
                        }
                    });
                    if(savedInstanceState != null) {
                        isFragmentDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);
                    }
                }
                if(selectedItem == 1) {
                    imageViewMap.setImageResource(R.drawable.walmartmap);
                    Toast.makeText(getApplicationContext(), "Click on the map to see details", Toast.LENGTH_LONG).show();
                    imageViewMap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(isFragmentDisplayed) {
                                closeFragmentWalmart();

                            } else {
                                displayFragmentWalmart();
                            }
                        }
                    });
                    if(savedInstanceState != null) {
                        isFragmentDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);
                    }
                }
                if(selectedItem == 2) {
                    imageViewMap.setImageResource(R.drawable.superstoremap);
                    Toast.makeText(getBaseContext(), "Click on the map to see details", Toast.LENGTH_LONG).show();
                    imageViewMap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(isFragmentDisplayed) {
                                closeFragmentSuperstore();

                            } else {
                                displayFragmentSuperstore();
                            }
                        }
                    });
                    if(savedInstanceState != null) {
                        isFragmentDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Drawer Navigation
        drawerLayout = findViewById(R.id.drawer_layout);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
    }

    private List<Stores> ReadFile() {
        List<Stores> ListOfStores = new ArrayList<>();

        InputStream inputStream = getResources().openRawResource(R.raw.storeinfo);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String csvLine;
            if((csvLine = reader.readLine()) != null) {

            }
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                int id = Integer.parseInt(row[0]);
                String storePicName = row[1];
                int storeDrawable = getResources().getIdentifier(storePicName, "drawable", getPackageName());
                String storeName = row[2];
                String storeAddress = row[3];
                String storeMapPicName = row[4];
                int storeMapDrawable = getResources().getIdentifier(storeMapPicName, "drawable", getPackageName());

                Stores eachStore = new Stores(id, storePicName, storeDrawable, storeName, storeAddress, storeMapPicName, storeMapDrawable);
                ListOfStores.add(eachStore);
            }
        } catch (Exception ex) {
            Log.d("FILELOG", ex.getMessage() + "File Process error");
        }
        return ListOfStores;
    }

    public void displayFragment() {
        StoreMapFragment storeMapFragment = StoreMapFragment.newInstance();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, storeMapFragment).addToBackStack(null).commit();
        isFragmentDisplayed = true;
    }

    public void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        StoreMapFragment simpleFragment = (StoreMapFragment) fragmentManager.findFragmentById(R.id.fragment_container);
        if(simpleFragment != null) {
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
        isFragmentDisplayed = false;
    }

    public void displayFragmentWalmart() {
        StoreMapFragmentWalmart storeMapFragmentWM = StoreMapFragmentWalmart.newInstance();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container_walmart, storeMapFragmentWM).addToBackStack(null).commit();
        isFragmentDisplayed = true;
    }

    public void closeFragmentWalmart() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        StoreMapFragmentWalmart simpleFragment = (StoreMapFragmentWalmart) fragmentManager.findFragmentById(R.id.fragment_container_walmart);
        if(simpleFragment != null) {
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
        isFragmentDisplayed = false;
    }

    public void displayFragmentSuperstore() {
        StoreMapFragmentSuperstore storeMapFragmentSS = StoreMapFragmentSuperstore.newInstance();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container_superstore, storeMapFragmentSS).addToBackStack(null).commit();
        isFragmentDisplayed = true;
    }

    public void closeFragmentSuperstore() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        StoreMapFragmentSuperstore simpleFragment = (StoreMapFragmentSuperstore) fragmentManager.findFragmentById(R.id.fragment_container_superstore);
        if(simpleFragment != null) {
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
        isFragmentDisplayed = false;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
        super.onSaveInstanceState(savedInstanceState);
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
        redirectActivity(this, CreateList.class);
    }



    public void ClickSearchStore(View view){
        redirectActivity(this, PermissionActivity.class);
    }

    public void ClickLogout(View view){
        logout(this);
    }

    public void ClickStoreMap(View view) {
        redirectActivity(this, StoreMap.class);
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
        Intent intent2 = new Intent(StoreMap.this, ShareList.class);
        startActivity(intent2);
    }
    public void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Back to login page
                Intent intent = new Intent(StoreMap.this, Login.class);
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
}